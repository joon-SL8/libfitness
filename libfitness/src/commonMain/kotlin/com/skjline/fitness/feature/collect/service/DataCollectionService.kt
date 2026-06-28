package com.skjline.fitness.feature.collect.service

import com.skjline.fitness.core.model.generic.DataCapture
import com.skjline.fitness.core.model.generic.PacketType
import com.skjline.fitness.core.model.generic.Pause
import com.skjline.fitness.core.model.generic.SetCourse
import com.skjline.fitness.core.model.generic.SetTargetPower
import com.skjline.fitness.core.model.generic.SetTimeSubscriber
import com.skjline.fitness.core.model.generic.Start
import com.skjline.fitness.core.model.generic.Stop
import com.skjline.fitness.core.model.packet.DataPacket
import com.skjline.fitness.core.model.packet.TargetPowerContent
import com.skjline.fitness.core.model.sensor.BikeTrainerSensor
import com.skjline.fitness.core.model.sensor.PowerControllable
import com.skjline.fitness.core.model.timer.IntervalTimeDataTimer
import com.skjline.fitness.core.model.timer.SessionTimeDataTimer
import com.skjline.fitness.core.model.timer.TargetPowerDataTimer
import com.skjline.fitness.core.model.workout.MrcCourse
import com.skjline.fitness.core.utils.DispatcherProvider
import com.skjline.fitness.feature.arrange.service.BluetoothSearchService
import com.skjline.fitness.feature.collect.model.CollectorState
import com.skjline.fitness.feature.collect.model.SearchMode
import com.skjline.fitness.feature.collect.model.SessionPause
import com.skjline.fitness.feature.collect.model.SessionStart
import com.skjline.fitness.feature.collect.model.SessionStop
import com.skjline.fitness.injection.AppComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.core.component.get
import kotlin.time.Clock.System.now
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
class DataCollectionService : ActivityDataCollector {
    private lateinit var course: MrcCourse
    private val dispatcherProvider: DispatcherProvider = AppComponent.get<DispatcherProvider>()

    private val _status = MutableSharedFlow<CollectorState>()

    private var ergScope: CoroutineScope? = null

    private val collectionTask = dispatcherProvider.io + Job()
    private val packetProvider = MutableSharedFlow<DataPacket>()
    private val dataProviders: MutableSet<DataCapture<*>> = mutableSetOf(
        IntervalTimeDataTimer(), TargetPowerDataTimer(),
    )

    private val dataCollector = mutableMapOf<PacketType, MutableList<DataPacket>>()
    private val timeProvider = SessionTimeDataTimer()

    var startedAt: Long = 0
        private set
    var duration: Long = 0
    private var powerOffset = 0

    val status = _status.asSharedFlow()

    private val powerConsumer by lazy {
        dataProviders.firstOrNull {
            it.getProvidingTypes().contains(PacketType.Power)
        }
    }

    private val powerSource by lazy {
        dataProviders.firstOrNull {
            it.getProvidingTypes().contains(PacketType.TargetPower)
        }
    }

    override fun collect(): Flow<DataPacket> {
        return packetProvider
    }

    override fun addCollector(collector: DataCapture<*>) {
        if (collector !in dataProviders) {
            collector.request(SetTimeSubscriber(timeProvider.asTimestampProvider))
            dataProviders.add(collector)
        }
        updateSessionStatus(SearchMode)
    }

    override fun beginSession(course: MrcCourse) {
        syncConnectedSensors()
        this.course = course
        dataCollector.clear()

        dataProviders.forEach(::prepareDataProvider)
        timeProvider.request(Start)
        startedAt = now().toEpochMilliseconds()
        updateSessionStatus(
            SessionStart(
                withERG = ergScope != null,
                timestamp = startedAt,
            )
        )
    }

    private fun syncConnectedSensors() {
        val bleService: BluetoothSearchService = AppComponent.get()
        bleService.getConnectedSensors().forEach { sensor ->
            addCollector(sensor)
        }
    }

    override fun pauseSession() {
        timeProvider.request(Pause)

        updateSessionStatus(SessionPause)
    }

    override fun stopSession() {
        timeProvider.request(Stop)

        val now = now().toEpochMilliseconds()
        duration = now - startedAt
        updateSessionStatus(
            SessionStop(duration = duration)
        )
    }

    private fun prepareDataProvider(provider: DataCapture<*>) {
        with(provider) {
            request(SetCourse(course))
            request(SetTimeSubscriber(timeProvider.asTimestampProvider))
            getProvidingTypes().forEach { key: PacketType ->
                if (!dataCollector.containsKey(key)) {
                    dataCollector[key] = mutableListOf()
                }
            }

            CoroutineScope(collectionTask).launch {
                observeDataPacket().collectLatest { packet ->
                    dataCollector[packet.type]?.add(packet)
                    packetProvider.emit(packet)
                }
            }
        }

        enableErgMode()
        provider.request(Start)
    }

    private fun updateSessionStatus(state: CollectorState) {
        CoroutineScope(collectionTask).launch {
            _status.emit(state)
        }
    }

    fun getProviderOf(packet: PacketType): DataCapture<*>? = if (packet == PacketType.TotalTime) {
        timeProvider
    } else {
        dataProviders.firstOrNull { packet in it.getProvidingTypes() }
    }

    fun getServiceList(): List<PacketType> {
        val services = mutableSetOf(PacketType.TotalTime)
        dataProviders.forEach { provider ->
            services.addAll(provider.getProvidingTypes())
        }

        println("available services: ${services.joinToString()}")
        return services.toList()
    }

    fun isErgModeEnabled() = ergScope != null

    override fun disableErgMode() {
        ergScope?.cancel()
        ergScope = null
    }

    override fun enableErgMode() {
        val consumer = powerConsumer
        if (powerSource == null || consumer == null || consumer !is PowerControllable) {
            println("Missing target source or controllable consumer for ERG Mode")
            return
        }

        disableErgMode()

        ergScope = CoroutineScope(dispatcherProvider.io + Job())
        ergScope?.launch {
            powerSource?.observeDataPacket()?.collectLatest { packet ->
                val level = (packet.data as TargetPowerContent).content
                consumer.setTargetPower(level).onFailure { e ->
                    println("Failed to write target power to trainer: ${e.message}")
                }
            }
        } ?: run { println("Unable to start an ERG Scope") }
    }

    override fun updatePowerOffset(power: Int): Int {
        powerOffset += power

        println("update power: $powerOffset($power)")
        powerSource?.request(SetTargetPower(powerOffset))

        return powerOffset
    }

    fun generateActivityFitFile() {
    }
}
