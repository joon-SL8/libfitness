package com.skjline.fitness.core.model.ble.data

data class GattFeatureCharacteristic(
    val data: String,
//    val featureCharacteristic: GattCharacteristic,
//    val characteristic: BluetoothGattCharacteristic,
//    val characteristics: List<BluetoothGattDescriptor>
)

data class GattFeatureService(
    val featureService: GattService,
//    val service: BluetoothGattService,
    val characteristics: List<GattFeatureCharacteristic>
)

data class GattCapability(
    val services: List<GattFeatureService>
)