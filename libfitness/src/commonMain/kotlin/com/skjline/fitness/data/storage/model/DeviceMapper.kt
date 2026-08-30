package com.skjline.fitness.data.storage.model

import com.skjline.fitness.core.model.ble.BluetoothComponent
import com.skjline.fitness.data.storage.Device

fun BluetoothComponent.map(): Device = Device(
    id = 0,
    name = this.name,
    uuid = this.uuid.first(),
    type = this.type.toString(),
    category = this.category,
    characteristics = this.resource,
)

fun Device.map(): BluetoothComponent = BluetoothComponent(
    id = id.toString(),
    name = this.name,
    uuid = this.uuid.let { listOf(it) } ?: listOf(),
    type = this.type?.toInt() ?: 0,
    category = this.category.orEmpty(),
    resource = this.category.orEmpty(),
)
