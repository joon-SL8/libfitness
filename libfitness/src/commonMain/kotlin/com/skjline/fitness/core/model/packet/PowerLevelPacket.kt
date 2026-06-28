package com.skjline.fitness.core.model.packet

data class PowerLevelPacket(
    val power: Int = 0,
) {
    companion object {
        fun fromPowerMeasurement(measurement: CyclingPowerMeasurementPacket): PowerLevelPacket =
            fromPayload(measurement.powerLevel)

        fun fromPayload(data: ByteArray): PowerLevelPacket =
            fromPayload(data.parseOctet())

        fun fromPayload(data: Int): PowerLevelPacket =
            PowerLevelPacket(data)
    }
}