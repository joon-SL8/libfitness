package com.skjline.fitness.core.model.sensor

import com.juul.kable.Peripheral
import com.skjline.fitness.core.model.ble.BluetoothComponent
import com.skjline.fitness.core.model.generic.DataProducer
import com.skjline.fitness.core.model.packet.DataPacket
import com.skjline.fitness.core.model.state.Connectivity
import com.skjline.fitness.core.model.state.Discovered
import com.skjline.fitness.core.model.state.Connecting
import com.skjline.fitness.core.model.state.Connected
import com.skjline.fitness.core.model.state.Disconnected
import com.skjline.fitness.core.model.state.Disconnecting
import com.skjline.fitness.core.utils.DispatcherProvider
import com.skjline.fitness.injection.AppComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.get
import kotlin.uuid.ExperimentalUuidApi

abstract class BLESensor<P : DataPacket>(
    val component: BluetoothComponent,
    protected val peripheral: Peripheral,
    protected val dispatcherProvider: DispatcherProvider = AppComponent.get(),
) : DataProducer<P> {
    protected val scope = CoroutineScope(dispatcherProvider.io + Job())

    abstract val observer: MutableSharedFlow<P>
    override fun observeDataPacket(): SharedFlow<P> = observer as SharedFlow<P>

    private val _connectivity = MutableStateFlow<Connectivity>(Discovered)
    val connectivity: StateFlow<Connectivity> = _connectivity.asStateFlow()

    abstract fun writeDescription(uuid: String, byteArray: ByteArray)

    @OptIn(ExperimentalUuidApi::class)
    protected open fun initialize() {
        println("Initializing BLE Sensor: $this")
        scope.launch {
            peripheral.services.collect { service ->
                println("service ${service?.joinToString { it.serviceUuid.toHexDashString() }}")
            }
        }

        scope.launch {
            peripheral.state.collect { state ->
                println("Device state: $state")
                when (state) {
                    is com.juul.kable.State.Connecting -> updateDeviceStatus(Connecting)
                    is com.juul.kable.State.Connected -> updateDeviceStatus(Connected)
                    is com.juul.kable.State.Disconnecting -> updateDeviceStatus(Disconnecting)
                    is com.juul.kable.State.Disconnected -> updateDeviceStatus(Disconnected)
                    else -> { /* Handle other states if necessary or ignore */
                    }
                }
            }
        }
    }

    protected open fun connect() {
        println("Request connect to peripheral: $this")
        scope.launch {
            println("Connect to Peripheral: $this")
            peripheral.connect()
        }
    }

    protected open fun disconnect() {
        scope.launch {
            println("Disconnect to Peripheral: $this")
            peripheral.disconnect()
        }
    }

    protected suspend fun updateDeviceStatus(status: Connectivity) {
        _connectivity.emit(status)
        println("on device status changed: $status")
    }

    protected suspend fun updateDataPacket(packet: P) {
        println("Updating packet: $packet")
        observer.emit(packet)
    }
}
