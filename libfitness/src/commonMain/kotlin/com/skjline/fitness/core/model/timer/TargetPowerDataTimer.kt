package com.skjline.fitness.core.model.timer

import com.skjline.fitness.core.model.generic.Action
import com.skjline.fitness.core.model.generic.PacketType
import com.skjline.fitness.core.model.generic.SetCourse
import com.skjline.fitness.core.model.generic.SetTargetPower
import com.skjline.fitness.core.model.generic.SetTimeSubscriber
import com.skjline.fitness.core.model.generic.Stop
import com.skjline.fitness.core.model.packet.DataPacket
import com.skjline.fitness.core.model.packet.TargetPower
import com.skjline.fitness.core.model.packet.TargetPowerContent
import com.skjline.fitness.core.model.workout.MrcCourse
import com.skjline.fitness.data.storage.Constants.Companion.PROFILE_KEY_FTP
import com.skjline.fitness.data.storage.StorageDatabase
import com.skjline.fitness.injection.AppComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.core.component.inject

/**
 * TargetPower Provider for a session.
 *
 * provides interval target power at a give timestamp for composable display
 */
class TargetPowerDataTimer : ObservableDataTimer<TargetPower>() {
    private var intervalIndex = 0
    private var course: MrcCourse? = null

    private val storage: StorageDatabase by AppComponent.inject()

    private var powerTarget: Int = 0
    private var powerAdjust: Int = 0

    override val observer = MutableStateFlow(TargetPower(data = TargetPowerContent(powerTarget, powerAdjust)))

    override fun request(action: Action) {
        when (action) {
            is Stop -> intervalIndex = 0
            is SetCourse -> setCourseData(action.course)
            is SetTimeSubscriber -> setExecutionTimeObserver(action.subscriber)
            is SetTargetPower -> setTargetPower(action.power)
            else -> {}
        }
    }

    private fun setCourseData(mrcCourse: MrcCourse) {
        course = mrcCourse
    }

    private fun setExecutionTimeObserver(timeObserver: Flow<Long>) {
        CoroutineScope(dispatcherProvider.io).launch {
            val ftps = storage.database.userProfileQueries.getAll().executeAsList()
            val ftp = ftps.firstOrNull { it.name == PROFILE_KEY_FTP }?.data_?.toInt() ?: 1
            println("member power: $ftp - ${ftps.joinToString()}")

            timeObserver.collectLatest { timestamp ->
                val size = course?.course?.size ?: 0
                if (intervalIndex > size)
                    return@collectLatest

                val eos = (course?.let { it.course[intervalIndex + 1].first } ?: 0.0f) *
                        DataPacket.MIN_TO_MILLIS
                if (timestamp >= eos) {
                    intervalIndex += 2
                    if (intervalIndex + 2 > size) {
                        updatePowerLevel(-1, powerAdjust)
                        return@collectLatest
                    }
                }

                val level = course?.let { it.course[intervalIndex + 1].second } ?: 0.0f
                powerTarget = (powerAdjust + (ftp * level) / 100).toInt()
                updatePowerLevel(powerTarget, powerAdjust)
            }
        }
    }

    private fun setTargetPower(power: Int) {
        if (powerAdjust == power) {
            return
        }

        println("member adjust: $powerAdjust => $power")
        powerAdjust = power
        CoroutineScope(dispatcherProvider.io).launch {
            updatePowerLevel(powerTarget, powerAdjust)
        }
    }

    private suspend fun updatePowerLevel(level: Int, offset: Int) {
        observer.emit(
            TargetPower(
                data = TargetPowerContent(level, offset)
            )
        )
    }

    override fun getProvidingTypes(): List<PacketType> =
        listOf(PacketType.TargetPower)
}
