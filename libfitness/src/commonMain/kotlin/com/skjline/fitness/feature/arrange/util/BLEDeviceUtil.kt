package com.skjline.fitness.feature.arrange.util

import com.juul.kable.Advertisement
import com.juul.kable.Peripheral
import com.skjline.fitness.core.model.ble.BluetoothComponent
import com.skjline.fitness.core.model.ble.data.GattService
import com.skjline.fitness.core.model.generic.Const.Companion.EMPTY
import com.skjline.fitness.core.model.sensor.BLESensor
import com.skjline.fitness.core.model.sensor.BikeTrainerSensor
import com.skjline.fitness.core.model.sensor.HRMSensor
import com.skjline.fitness.core.model.sensor.PowerSensor
import kotlinx.coroutines.CoroutineScope
import kotlin.uuid.ExperimentalUuidApi

class BLEDeviceUtil {
    @OptIn(ExperimentalUuidApi::class)
    companion object {
        fun createBleDevice(
            device: BluetoothComponent,
            scope: CoroutineScope,
            advertisement: Advertisement,
        ): BLESensor<*>? = when {
            GattService.Fitness_Machine.uuid in device.uuid -> {
                val peripheral = Peripheral(advertisement)
                BikeTrainerSensor(device, peripheral)
            }

            GattService.Cycling_Power.uuid in device.uuid ->
                PowerSensor(device, Peripheral(advertisement))

            GattService.Cycling_Speed_and_Cadence.uuid in device.uuid ->
                PowerSensor(device, Peripheral(advertisement))

            GattService.Heart_Rate.uuid in device.uuid ->
                HRMSensor(device, Peripheral(advertisement))

            else -> null
        }

        fun getPrimaryService(ad: Advertisement): String {
            val listOfServiceUUID = ad.uuids.map { it.toString() }
            return when {
                GattService.Fitness_Machine.uuid in listOfServiceUUID -> GattService.Fitness_Machine.uuid
                GattService.Cycling_Power.uuid in listOfServiceUUID -> GattService.Cycling_Power.uuid
                GattService.Cycling_Speed_and_Cadence.uuid in listOfServiceUUID -> GattService.Cycling_Speed_and_Cadence.uuid
                GattService.Heart_Rate.uuid in listOfServiceUUID -> GattService.Heart_Rate.uuid
                else -> EMPTY
            }
        }

        val serviceTags = listOf(
            GattService.Fitness_Machine.uuid,
            GattService.Cycling_Power.uuid,
            GattService.Cycling_Speed_and_Cadence.uuid,
            GattService.Heart_Rate.uuid
        )

    }
}