package com.skjline.fitness.feature.arrange.service

import com.juul.kable.PlatformScanner
import com.juul.kable.Scanner
import com.skjline.fitness.core.model.ble.BluetoothComponent
import com.skjline.fitness.core.model.ble.BluetoothComponent.Companion.toBluetoothDevice
import com.skjline.fitness.core.model.state.ScanError
import com.skjline.fitness.core.model.packet.DataPacket
import com.skjline.fitness.core.utils.DispatcherProvider
import com.skjline.fitness.core.model.sensor.BLESensor
import com.skjline.fitness.core.model.generic.Connect
import com.skjline.fitness.core.model.generic.Disconnect
import com.skjline.fitness.core.model.state.Operation
import com.skjline.fitness.core.model.state.Init
import com.skjline.fitness.core.model.state.OnDeviceUpdated
import com.skjline.fitness.core.model.state.Connected
import com.skjline.fitness.feature.arrange.util.BLEDeviceUtil
import com.skjline.fitness.injection.AppComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.core.component.get
import kotlin.time.Duration.Companion.milliseconds
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class BluetoothSearchService {

    private val dispatcherProvider: DispatcherProvider = AppComponent.get<DispatcherProvider>()

    private var scopeSearch: CoroutineScope? = null
    private val scope = CoroutineScope(dispatcherProvider.io + Job())

    private val remoteDevices: MutableMap<String, BLESensor<*>> = mutableMapOf<String, BLESensor<*>>()
    private val scanner: PlatformScanner by lazy {
        Scanner {
            filters {
                match {
                    services = BLEDeviceUtil.serviceTags.map { Uuid.parse(it) }
                }
            }
        }
    }

    private val _scanDeviceState = MutableStateFlow<Operation>(Init)
    val scanDeviceState: StateFlow<Operation> = _scanDeviceState.asStateFlow()

    fun startScanning() {
        println("starting BLE scanning")
        val timeout = CoroutineScope(dispatcherProvider.io + Job())
        timeout.launch {
            // 30 sec delay
            delay(SEARCH_TIMEOUT.milliseconds)

            _scanDeviceState.emit(ScanError)

            println("Scanning timed out")
            scopeSearch?.cancel("timeout reached")
        }

        scopeSearch = CoroutineScope(dispatcherProvider.io + Job())
        scopeSearch?.launch {
            println("Scanning Started")
            scanner.advertisements.collectLatest { advertisement ->
                val key: String = advertisement.identifier.toString()
                println("Scanning Advertisement: $key")

                // Scanner filter didn't work. Advertisement yields service uuid and MAC (identifier)
                val found =
                    advertisement.uuids.any { uuid -> uuid.toString() in BLEDeviceUtil.serviceTags }
                if (found && !remoteDevices.keys.contains(key)) {
                    timeout.cancel()

                    advertisement.uuids.forEach {
                        advertisement.serviceData(it)
                    }

                    val device = advertisement.toBluetoothDevice()
                        .copy(uuid = advertisement.uuids.map { it.toString() })
                    BLEDeviceUtil.createBleDevice(device, scope, advertisement)?.let {
                        remoteDevices[device.id] = it
                    }
                    _scanDeviceState.emit(
                        OnDeviceUpdated(remoteDevices.values.toList())
                    )
                }
                // remove?
            }
        }
    }

    fun stopScanning() {
        scopeSearch?.cancel()
    }

    fun connectToDevice(device: BluetoothComponent): Flow<DataPacket>? =
        remoteDevices[device.id]?.let { trainerBLEDevice ->
            println("Connecting to device: ${trainerBLEDevice.component.name}")
            trainerBLEDevice.request(Connect)
            trainerBLEDevice.observeDataPacket()
        }

    fun disconnectToDevices(device: BluetoothComponent? = null): Flow<DataPacket>? {
        device?.let {
            println("Disonnecting to device: ${device.name}")
            remoteDevices[it.id]?.request(Disconnect)
        } ?: run {
            remoteDevices.values.forEach { it.request(Disconnect) }
            remoteDevices.clear()
            scope.cancel()
        }

        return null
    }

    fun getConnectedSensors(): List<BLESensor<*>> {
        val sensors = remoteDevices.values.filter { it.connectivity.value == Connected }
        println("Connected Sensors: ${sensors.joinToString { it.component.name }}")
        return sensors
    }

    fun collectConnectedDevice(deviceIds: List<String>): List<BLESensor<*>> {
        val sensors = remoteDevices.filter { it.key in deviceIds }.values.toList()
        println("Collecting Sensors: ${sensors.joinToString { it.component.name }}")
        return sensors
    }

    private companion object {
        const val SEARCH_TIMEOUT = 30_000L // 30 sec searching period
    }
}
