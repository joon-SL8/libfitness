package com.skjline.fitness.core.model.packet

data class HeartRatePacket(
    val hrData: Int = 0,
) {
    companion object {
        fun fromPayload(data: ByteArray): HeartRatePacket =
            HeartRatePacket(data[1].toInt())
    }
}
