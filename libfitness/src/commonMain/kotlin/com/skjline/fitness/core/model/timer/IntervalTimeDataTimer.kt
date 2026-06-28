package com.skjline.fitness.core.model.timer

import com.skjline.fitness.core.model.generic.Action
import com.skjline.fitness.core.model.generic.PacketType
import com.skjline.fitness.core.model.generic.SetCourse
import com.skjline.fitness.core.model.generic.SetTimeSubscriber
import com.skjline.fitness.core.model.generic.Stop
import com.skjline.fitness.core.model.packet.DataPacket.Companion.MIN_TO_MILLIS
import com.skjline.fitness.core.model.packet.Interval
import com.skjline.fitness.core.model.packet.SimpleLongContent
import com.skjline.fitness.core.model.workout.MrcCourse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * Interval Timer Provider for a session.
 *
 * Interval Time is a time span set for holding the target power.
 */
class IntervalTimeDataTimer : ObservableDataTimer<Interval>() {
    private var intervalIndex = 0
    private var course: MrcCourse? = null

    override val observer = MutableStateFlow(Interval(data = SimpleLongContent(0)))

    override fun request(action: Action) {
        when (action) {
            is Stop -> stop()
            is SetCourse -> setCourseData(action.course)
            is SetTimeSubscriber -> setExecutionTimeObserver(action.subscriber)
            else -> {}
        }
    }

    private fun stop() {
        intervalIndex = 0
    }

    private fun setCourseData(course: MrcCourse) {
        this.course = course
    }

    private fun setExecutionTimeObserver(timeObserver: Flow<Long>) {
        CoroutineScope(dispatcherProvider.io).launch {
            timeObserver.collectLatest { timestamp ->
                val size = course?.course?.size ?: 0
                if (intervalIndex > size)
                    return@collectLatest

                val eos = (course?.let { it.course[intervalIndex + 1].first } ?: 0.0f) * MIN_TO_MILLIS
                if (timestamp >= eos) {
                    intervalIndex += 2
                    if (intervalIndex + 2 > size) {
                        observer.emit(Interval(data = SimpleLongContent(-1)))
                        return@collectLatest
                    }
                }

                val start = course?.let { it.course[intervalIndex + 1].first } ?: 0.0f
                val remaining = (start * MIN_TO_MILLIS) - timestamp
                observer.emit(Interval(data = SimpleLongContent(remaining.toLong())))
            }
        }
    }

    override fun getProvidingTypes(): List<PacketType> =
        listOf(PacketType.Interval)
}
