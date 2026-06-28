package com.skjline.fitness.core.model.packet

import com.skjline.fitness.core.model.generic.PacketType
import com.skjline.fitness.core.model.packet.DataPacket.Companion.SEC_TO_MILLIS
import io.ktor.util.date.GMTDate
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

sealed interface DataPacket {
    val timestamp: Long
    val type: PacketType
    val data: PacketContent

    fun formatter(): String
    fun dataWithTimestamp(): String

    companion object {
        const val SEC_TO_MILLIS = 1000
        const val MIN_TO_MILLIS = 60 * SEC_TO_MILLIS
    }
}

@OptIn(ExperimentalTime::class)
data object Initial : DataPacket {
    override val timestamp = Clock.System.now().epochSeconds
    override val type: PacketType = PacketType.Unknown
    override val data: PacketContent = SimpleLongContent(content = 0L)

    override fun formatter(): String =
        (data as? SimpleLongContent)?.content?.let {
            "$it ${type.unit}"
        } ?: run {
            println("data type: ${data::class.simpleName} (expect: SimpleLongContent)")
            "---"
        }

    override fun dataWithTimestamp(): String =
        "${timestamp}:${(data as? SimpleLongContent)?.content}"
}

data class Cadence(
    override val type: PacketType = PacketType.Cadence,
    override val data: PacketContent,
) : DataPacket by Initial

data class Speed(
    override val type: PacketType = PacketType.Speed,
    override val data: PacketContent,
) : DataPacket by Initial

data class HRData(
    override val type: PacketType = PacketType.HRData,
    override val data: PacketContent,
) : DataPacket by Initial {
    override fun formatter(): String =
        with((data as HeartRateContent).content) {
            "$hrData ${type.unit}"
        }

    override fun dataWithTimestamp(): String =
        "${timestamp}:${(data as? HeartRateContent)?.content}"
}

data class Power(
    override val type: PacketType = PacketType.Power,
    override val data: PacketContent,
) : DataPacket by Initial

data class TargetPower(
    override val type: PacketType = PacketType.TargetPower,
    override val data: PacketContent,
) : DataPacket by Initial {
    override fun formatter(): String =
        ((data as? TargetPowerContent)?.let {
            "${it.content}" + if (it.offset != 0) {
                "(${it.offset})"
            } else {
                ""
            }
        } ?: run {
            println("data type: ${data::class.simpleName} (expect: TargetPowerContent)")
            "---"
        }) + " ${type.unit}"
}

data class Interval(
    override val type: PacketType = PacketType.Interval,
    override val data: PacketContent,
) : DataPacket by Initial {
    override fun formatter(): String =
        (data as? SimpleLongContent)?.content?.toElapsedTime() ?: run {
            println("data type: ${data::class.simpleName} (expect: SimpleLongContent)")
            "---"
        }
}

data class TotalTime(
    override val type: PacketType = PacketType.TotalTime,
    override val data: PacketContent,
) : DataPacket by Initial {
    override fun formatter(): String = (data as? TotalTimeContent)?.content?.toElapsedTime() ?: run {
        println("data type: ${data::class.simpleName} (expect: TotalTimeContent)")
        "---"
    }

    override fun dataWithTimestamp(): String =
        "${timestamp}:${(data as? TotalTimeContent)?.content}"
}

data class Battery(
    override val type: PacketType = PacketType.Battery,
    override val data: PacketContent,
) : DataPacket by Initial

internal fun Long.toElapsedTime(): String {
    val date = GMTDate(timestamp = this)
    val hour = date.hours.toTimeString()
    val min = date.minutes.toTimeString()
    val secs = date.seconds.toTimeString()
    val millis = ((this % SEC_TO_MILLIS) / 100).toInt()
    val time = "$hour:$min:$secs.$millis"
    return time
}

private fun Int.toTimeString(default: String = "0") = when {
    this > 9 -> "$this"
    this > 0 -> "$default$this"
    else -> default.repeat(2)
}
