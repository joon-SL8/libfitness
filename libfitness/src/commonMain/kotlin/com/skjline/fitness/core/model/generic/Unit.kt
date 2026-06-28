package com.skjline.fitness.core.model.generic

sealed interface Unit {
    val distance: String
    val weight: String

    fun toLongDistance(unit: Long): String

    fun Long.convertFeetToMeters(): Long =
        (this / FEET_TO_METER).toLong()

    fun Long.convertMetersToFeet(): Long =
        (this * FEET_TO_METER).toLong()

    fun Long.lbToKg(): Float =
        (this * LB_TO_KG).toFloat()

    fun Long.kgToLb(): Float =
        (this / LB_TO_KG).toFloat()

    private companion object {
        const val FEET_TO_METER = 3.28084
        const val LB_TO_KG = 0.453592
    }
}

data class Imperial(
    override val distance: String = "ft",
    override val weight: String = "lb",
) : Unit {
    override fun toLongDistance(unit: Long): String {
        val mi = unit / FEET_TO_MILE
        val f = (unit % FEET_TO_MILE) * (100 / FEET_TO_MILE)
        return "${mi}${f}"
    }

    private companion object {
        const val FEET_TO_MILE = 5280
    }
}

data class Metric(
    override val distance: String = "m",
    override val weight: String = "kg",
) : Unit {
    override fun toLongDistance(unit: Long): String {
        val mi = unit / 1000
        val f = unit % 1000
        return "${mi}.${f}Km"
    }
}
