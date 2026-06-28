package com.skjline.fitness.feature.arrange.util

interface BleUtils {
    companion object {
        fun getBluetoothCategory(category: Int): BluetoothCategory =
            BluetoothCategory.entries.firstOrNull {
                it.major == category
            } ?: BluetoothCategory.Uncategorized

        fun getBluetoothTypeName(type: Int): BluetoothTypeName =
            BluetoothTypeName.entries.firstOrNull {
                it.type == type
            } ?: BluetoothTypeName.Unknown
    }
}

enum class BluetoothCategory(val major: Int, val typeName: String) {
    Computer(256, "Computer"),
    Phone(512, "Phone"),
    Networking(768, "Networking"),
    Audio(1024, "Audio"),
    Peripherals(1280, "Peripherals"),
    Imaging(1536, "Imaging"),
    Wearable(1792, "Wearable"),
    Toy(2048, "Toy"),
    Health(2304, "Health"),
    Uncategorized(7936, "Uncategorized"),
    IndoorBike(10962, "Indoor Bike"),
}

enum class BluetoothTypeName(val type: Int, val typeName: String) {
    Classic(1, "Classic"),
    LE(2, "BLE"),
    Dual(3, "Dual"),
    Unknown(0, "Classic"),
}