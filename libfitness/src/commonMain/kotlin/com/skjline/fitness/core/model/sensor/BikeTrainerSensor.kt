package com.skjline.fitness.core.model.sensor

import com.juul.kable.Characteristic
import com.juul.kable.Peripheral
import com.juul.kable.State
import com.juul.kable.WriteType
import com.juul.kable.characteristicOf
import com.skjline.fitness.core.model.ble.BluetoothComponent
import com.skjline.fitness.core.model.ble.data.GattCharacteristic
import com.skjline.fitness.core.model.ble.data.GattService
import com.skjline.fitness.core.model.generic.Action
import com.skjline.fitness.core.model.generic.Connect
import com.skjline.fitness.core.model.generic.Disconnect
import com.skjline.fitness.core.model.generic.Initialize
import com.skjline.fitness.core.model.generic.PacketType
import com.skjline.fitness.core.model.generic.Pause
import com.skjline.fitness.core.model.generic.Resume
import com.skjline.fitness.core.model.generic.SetCourse
import com.skjline.fitness.core.model.generic.SetTargetPower
import com.skjline.fitness.core.model.generic.SetTimeSubscriber
import com.skjline.fitness.core.model.generic.Start
import com.skjline.fitness.core.model.generic.Stop
import com.skjline.fitness.core.model.packet.Cadence
import com.skjline.fitness.core.model.packet.CyclingPowerMeasurementPacket
import com.skjline.fitness.core.model.packet.DataPacket
import com.skjline.fitness.core.model.packet.Power
import com.skjline.fitness.core.model.packet.PowerControlCommands
import com.skjline.fitness.core.model.packet.Speed
import com.skjline.fitness.core.model.packet.SimpleLongContent
import com.skjline.fitness.core.model.state.Connected
import com.skjline.fitness.core.model.state.Connecting
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import okio.ByteString.Companion.toByteString
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class BikeTrainerSensor(
    component: BluetoothComponent,
    peripheral: Peripheral,
) : BLESensor<DataPacket>(component, peripheral),
    PowerObservable,
    CadenceObservable,
    SpeedObservable,
    PowerControllable {

    override val observer: MutableSharedFlow<DataPacket> =
        MutableSharedFlow(replay = 0, onBufferOverflow = BufferOverflow.DROP_OLDEST, extraBufferCapacity = 16)

    override val powerFlow: Flow<Power> = observer.filterIsInstance()
    override val cadenceFlow: Flow<Cadence> = observer.filterIsInstance()
    override val speedFlow: Flow<Speed> = observer.filterIsInstance()

    private val powerCommands = PowerControlCommands()

    private val powerCharacteristic: Characteristic = characteristicOf(
        Uuid.parse(GattService.Cycling_Power.uuid),
        Uuid.parse(
            GattCharacteristic.Cycling_Power_Measurement.uuid
        )
    )
    private val controlCharacteristic: Characteristic = characteristicOf(
        Uuid.parse(GattService.Fitness_Machine.uuid),
        Uuid.parse(
            GattCharacteristic.Fitness_Machine_Control_Point.uuid
        )
    )

    private var connectionJob: Job? = null
    private var lastCyclePlot: CyclingPowerMeasurementPacket? = null
    private var lastWheelPlot: CyclingPowerMeasurementPacket? = null

    override fun connect() {
        super.connect()

        connectionJob?.cancel()
        connectionJob = scope.launch {
            peripheral.state.collectLatest { state ->
                println("BLE Status: $state")
                when (state) {
                    is State.Connecting -> updateDeviceStatus(Connecting)
                    is State.Connected -> onConnected()
                    is State.Disconnecting -> updateDeviceStatus(com.skjline.fitness.core.model.state.Disconnecting)
                    is State.Disconnected -> updateDeviceStatus(com.skjline.fitness.core.model.state.Disconnected)
                    else -> {}
                }
            }
        }
    }

    private suspend fun onConnected() {
        runCatching {
            println("service: request control")
            peripheral.write(
                controlCharacteristic,
                byteArrayOf(FMCP_REQUEST_CONTROL),
                WriteType.WithResponse
            )

            updateDeviceStatus(Connected)

            println("service: observe data updates")
            peripheral.observe(powerCharacteristic).collect { bytes ->
                val data = CyclingPowerMeasurementPacket.fromPayload(bytes)
                val rpm = CyclingPowerMeasurementPacket.calculateRPM(data, lastCyclePlot)
                val pwr = data.powerLevel.toLong()
                println("data - raw: ${bytes.toByteString()}")

                println("data: RPM: $rpm")
                observer.emit(Cadence(data = SimpleLongContent(rpm)))

                CyclingPowerMeasurementPacket.calculateSpeed(data, lastCyclePlot)?.let { speedMph ->
                    println("data: SPD: $speedMph")
                    observer.emit(Speed(data = SimpleLongContent(speedMph.toLong())))
                }

                lastCyclePlot = data
                println("data: PWR: $pwr")
                observer.emit(Power(data = SimpleLongContent(pwr)))
            }
        }.onFailure { e ->
            println("Error in BLE observation loop: ${e.message}")
            updateDeviceStatus(com.skjline.fitness.core.model.state.Disconnected)
        }
    }

    private fun start() {
        println("writing control point char for start")
        peripheral.writeControlWithResponse(powerCommands.getStartCommand())
    }

    private fun stop() {
        println("writing control point char for stop")
        peripheral.writeControlWithResponse(powerCommands.getStopCommand())
    }

    private fun pause() {
        println("writing control point char for pause")
        peripheral.writeControlWithResponse(powerCommands.getPauseCommand())
    }

    override fun request(action: Action) {
        when (action) {
            is Connect -> {}
            is Disconnect -> {}
            is Pause -> pause()
            is Initialize -> {}
            is SetCourse -> {}
            is SetTimeSubscriber -> {}
            is Start, Resume -> start()
            is Stop -> stop()
            is SetTargetPower -> {
                scope.launch {
                    setTargetPower(action.power)
                }
            }
        }
    }

    override fun writeDescription(uuid: String, byteArray: ByteArray) {}

    override fun getProvidingTypes(): List<PacketType> =
        listOf(PacketType.Power, PacketType.Cadence, PacketType.Speed)

    override suspend fun setTargetPower(watts: Int): Result<Unit> = runCatching {
        val targetPowerCommand = powerCommands.getSetTargetPowerCommand(watts)
        println("set target power: $watts")
        peripheral.write(controlCharacteristic, targetPowerCommand, WriteType.WithResponse)
    }

    private fun Peripheral.writeControlWithResponse(
        data: ByteArray,
        characteristic: Characteristic = controlCharacteristic,
    ) {
        scope.launch {
            runCatching {
                write(characteristic, data, WriteType.WithResponse)
            }.onFailure { e ->
                println("Failed to write control command: ${e.message}")
            }
        }
    }

    private companion object Companion {
        // Fitness Machine Control Procedure Commands & Values
        const val FMCP_REQUEST_CONTROL: Byte = 0x00
    }
}
