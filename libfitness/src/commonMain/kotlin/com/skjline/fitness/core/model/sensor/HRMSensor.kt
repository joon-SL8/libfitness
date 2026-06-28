package com.skjline.fitness.core.model.sensor

import com.juul.kable.Characteristic
import com.juul.kable.Peripheral
import com.juul.kable.characteristicOf
import com.skjline.fitness.core.model.ble.BluetoothComponent
import com.skjline.fitness.core.model.ble.data.GattCharacteristic
import com.skjline.fitness.core.model.ble.data.GattService
import com.skjline.fitness.core.model.generic.Action
import com.skjline.fitness.core.model.generic.PacketType
import com.skjline.fitness.core.model.packet.HRData
import com.skjline.fitness.core.model.packet.HeartRateContent
import com.skjline.fitness.core.model.packet.HeartRatePacket
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class HRMSensor(
    component: BluetoothComponent,
    peripheral: Peripheral,
) : BLESensor<HRData>(component, peripheral) {
    override val observer: MutableStateFlow<HRData> =
        MutableStateFlow(HRData(data = HeartRateContent(HeartRatePacket(hrData = 0))))

    private val characteristic: Characteristic = characteristicOf(
        Uuid.parse(GattService.Heart_Rate.uuid),
        Uuid.parse(
            GattCharacteristic.Heart_Rate_Measurement.uuid
        )
    )

    override fun connect() {
        super.connect()
        scope.launch {
            println("Connecting to HRM Peripheral: $this")
            peripheral.observe(characteristic).collectLatest { bytes ->
                val data = HeartRateContent(HeartRatePacket.fromPayload(bytes))
                println("Collecting HRM Peripheral Data: $data")
                updateDataPacket(HRData(data = data))
            }
        }
    }

    override fun writeDescription(uuid: String, byteArray: ByteArray) {
        /* read only device - no need to implement */
    }

    override fun request(action: Action) {
    }

    override fun getProvidingTypes(): List<PacketType> =
        listOf(PacketType.HRData)

    private companion object Companion {
        const val CHARACTER_SIZE = 2

        const val INDEX_BATTERY_LEVEL = 0
        const val INDEX_HR_MEASUREMENT = 1
    }
}
