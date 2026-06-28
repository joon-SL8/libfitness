package com.skjline.fitness.core.model.sensor

import com.juul.kable.Characteristic
import com.juul.kable.Peripheral
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
import com.skjline.fitness.core.model.generic.SetCourse
import com.skjline.fitness.core.model.generic.SetTargetPower
import com.skjline.fitness.core.model.generic.SetTimeSubscriber
import com.skjline.fitness.core.model.generic.Start
import com.skjline.fitness.core.model.generic.Stop
import com.skjline.fitness.core.model.packet.CyclingPowerMeasurementPacket
import com.skjline.fitness.core.model.packet.Power
import com.skjline.fitness.core.model.packet.PowerContent
import com.skjline.fitness.core.model.packet.SimpleLongContent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class PowerSensor(
    component: BluetoothComponent,
    peripheral: Peripheral,
) : BLESensor<Power>(component, peripheral) {
    override val observer = MutableStateFlow(Power(data = SimpleLongContent(0L)))

    private val powerCharacteristic: Characteristic = characteristicOf(
        Uuid.parse(GattService.Cycling_Power.uuid),
        Uuid.parse(
            GattCharacteristic.Cycling_Power_Measurement.uuid
        )
    )

    override fun connect() {
        super.connect()
        scope.launch {
            peripheral.observe(powerCharacteristic).collectLatest { bytes ->
                val data = CyclingPowerMeasurementPacket.fromPayload(bytes)
                updateDataPacket(Power(data = PowerContent(data)))
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
        listOf(PacketType.Power)
}
