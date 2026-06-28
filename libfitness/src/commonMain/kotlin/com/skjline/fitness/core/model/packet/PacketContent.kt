package com.skjline.fitness.core.model.packet

sealed interface PacketContent {
    fun getValueAsNumber(): Int
}

data class SimpleLongContent(
    val content: Long,
) : PacketContent {
    override fun getValueAsNumber(): Int = content.toInt()
}

data class TotalTimeContent(
    val content: Long,
) : PacketContent {
    override fun getValueAsNumber(): Int = content.toInt()
}

data class PowerContent(
    val content: CyclingPowerMeasurementPacket,
) : PacketContent {
    override fun getValueAsNumber(): Int = content.powerLevel
}

data class TargetPowerContent(
    val content: Int,
    val offset: Int,
) : PacketContent {
    override fun getValueAsNumber(): Int = content
}

data class HeartRateContent(
    val content: HeartRatePacket,
) : PacketContent {
    override fun getValueAsNumber(): Int = content.hrData
}
