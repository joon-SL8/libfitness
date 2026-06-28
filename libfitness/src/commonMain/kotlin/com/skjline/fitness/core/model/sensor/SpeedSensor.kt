package com.skjline.fitness.core.model.sensor

import com.juul.kable.Characteristic
import com.juul.kable.Peripheral
import com.juul.kable.characteristicOf
import com.skjline.fitness.core.model.generic.PacketType
import com.skjline.fitness.core.model.ble.BluetoothComponent
import com.skjline.fitness.core.model.ble.data.GattCharacteristic
import com.skjline.fitness.core.model.ble.data.GattService
import com.skjline.fitness.core.model.generic.Action
import com.skjline.fitness.core.model.generic.Connect
import com.skjline.fitness.core.model.generic.Disconnect
import com.skjline.fitness.core.model.generic.Initialize
import com.skjline.fitness.core.model.generic.Pause
import com.skjline.fitness.core.model.generic.SetCourse
import com.skjline.fitness.core.model.generic.SetTargetPower
import com.skjline.fitness.core.model.generic.SetTimeSubscriber
import com.skjline.fitness.core.model.generic.Start
import com.skjline.fitness.core.model.generic.Stop
import com.skjline.fitness.core.model.packet.Power
import com.skjline.fitness.core.model.packet.SimpleLongContent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class SpeedSensor(
    component: BluetoothComponent,
    peripheral: Peripheral,
) : BLESensor<Power>(component, peripheral) {
    override val observer = MutableStateFlow(Power(data = SimpleLongContent(0L)))

    @OptIn(ExperimentalUuidApi::class)
    private val speedCadenceCharacteristic: Characteristic = characteristicOf(
        Uuid.parse(GattService.Cycling_Speed_and_Cadence.uuid),
        Uuid.parse(GattCharacteristic.CSC_Measurement.uuid) // Corrected here
    )

    override fun connect() {
        super.connect()
        scope.launch {
            peripheral.observe(speedCadenceCharacteristic).collectLatest { bytes ->
                // TODO: Implement proper parsing for Cycling Speed and Cadence Measurement
                // For now, emit a placeholder Power packet as the observer is of type Power
                // A better approach would be to have a dedicated SpeedPacket and change observer type
                updateDataPacket(Power(data = SimpleLongContent(bytes.size.toLong()))) // Placeholder for speed data
            }
        }
    }

    override fun writeDescription(uuid: String, byteArray: ByteArray) {}

    override fun request(action: Action) {
        when (action) {
            is Connect -> connect()
            is Disconnect -> {}
            is Start, Stop -> {}
            is Pause -> {}
            is Initialize -> {}
            is SetCourse -> {}
            is SetTargetPower -> {}
            is SetTimeSubscriber -> {}
        }
    }

    override fun getProvidingTypes(): List<PacketType> =
        listOf(PacketType.Speed)
}