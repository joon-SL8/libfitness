package com.skjline.fitness.core.model.timer

import com.skjline.fitness.core.model.generic.Action
import com.skjline.fitness.core.model.generic.PacketType
import com.skjline.fitness.core.model.generic.Pause
import com.skjline.fitness.core.model.generic.SetCourse
import com.skjline.fitness.core.model.generic.SetTimeSubscriber
import com.skjline.fitness.core.model.generic.Start
import com.skjline.fitness.core.model.generic.Stop
import com.skjline.fitness.core.model.packet.TotalTime
import com.skjline.fitness.core.model.packet.TotalTimeContent
import com.skjline.fitness.core.model.workout.MrcCourse
import com.skjline.fitness.core.utils.DispatcherProvider
import com.skjline.fitness.injection.AppComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.koin.core.component.get
import kotlin.coroutines.CoroutineContext

/**
 * Time Provider for a session.
 *
 * serves a dual purpose - could revisit for separating its responsibilities
 * provides timestamp for the active session for other data providers
 * provides time data to composable for display
 */
class SessionTimeDataTimer : ObservableDataTimer<TotalTime>() {
    private var timerJob: CoroutineContext? = null
    private var course: MrcCourse? = null

    override val observer = MutableStateFlow(TotalTime(data = TotalTimeContent(0L)))

    private var active = false
    val asTimestampProvider: Flow<Long> = observer.map {
        (it.data as TotalTimeContent).content
    }

    override fun request(action: Action) {
        when (action) {
            is Start -> startTimer()
            is Pause -> pauseTimer()
            is Stop -> stopTimer()
            is SetCourse -> setCourseData(action.course)
            is SetTimeSubscriber -> setExecutionTimeObserver(action.subscriber)
            else -> {}
        }
    }

    private fun startTimer() {
        timerJob ?: run { timerJob = dispatcherProvider.default + Job() }
        CoroutineScope(timerJob!!).launch {
            active = true
            while (active) {
                delay(100L)
                val elapsed = (observer.value.data as TotalTimeContent).content
                val update = TotalTime(
                    data = TotalTimeContent(content = elapsed + 100L)
                )

                observer.emit(update)
            }
        }
    }

    private fun pauseTimer() {
        try {
            active = false
        } catch (ex: Exception) {
        }
    }

    private fun stopTimer() {
        pauseTimer()
        try {
            timerJob?.cancel()
            timerJob = null
            observer.value = TotalTime(
                data = TotalTimeContent(0L)
            )
        } catch (ex: Exception) {
        }
    }

    private fun setCourseData(mrcCourse: MrcCourse) {
        course = mrcCourse
    }

    private fun setExecutionTimeObserver(timeObserver: Flow<Long>) {
        /* intentionally left blank - no need to observe time provided by itself */
    }

    override fun getProvidingTypes() =
        listOf(PacketType.TotalTime)
}