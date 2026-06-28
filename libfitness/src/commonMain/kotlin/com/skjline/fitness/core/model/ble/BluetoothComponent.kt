package com.skjline.fitness.core.model.ble

import com.juul.kable.Advertisement
import com.skjline.fitness.core.model.generic.Const.Companion.EMPTY
import com.skjline.fitness.core.model.ble.data.GattService
import kotlinx.serialization.Serializable
import kotlin.uuid.ExperimentalUuidApi

@Serializable
@OptIn(ExperimentalUuidApi::class)
data class BluetoothComponent(
    val id: String = EMPTY,
    val resource: String = EMPTY,
    val type: Int = 0,
    val name: String = EMPTY,
    val category: String = EMPTY,
    val address: String = EMPTY,
    val uuid: List<String> = emptyList(),
) {
    companion object {

        fun BluetoothComponent.toBluetoothDevice(
            icon: String = EMPTY,
            uuid: String = EMPTY,
        ) = BluetoothComponent(
            id = EMPTY,
            resource = icon,
            type = this.type,
            name = this.name,
//            category = this.bluetoothClass.deviceClass.toString(),
            address = this.address,
            uuid = emptyList(),
        )

        fun BluetoothComponent.applyIconResource(): BluetoothComponent = copy(
            resource = when {
                uuid.contains(GattService.Fitness_Machine.uuid) -> "icon_fms"
                uuid.contains(GattService.Cycling_Power.uuid) -> "icon_power"
                uuid.contains(GattService.Cycling_Speed_and_Cadence.uuid) -> "icon_sac"
                uuid.contains(GattService.Heart_Rate.uuid) -> "icon_hr"
                else -> EMPTY
            }
        )

        fun Advertisement.toBluetoothDevice(
            icon: String = EMPTY,
        ): BluetoothComponent {
            val id: String = identifier.toString()
            return BluetoothComponent(
                id = id,
                resource = icon,
                name = name.orEmpty(),
                address = id,
                uuid = uuids.map { it.toString() },
            )
        }
    }
}