package com.skjline.fitness.feature.publish.fit.model

import com.skjline.fitness.core.model.generic.Const.Companion.EMPTY
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class SportType(val typeId: Int = 0) {
    data object Cycling : SportType(typeId = 2)
    data object Running : SportType(typeId = 1)
}

@Serializable
sealed class FitType {
    data object Activity : FitType()
    data object Workout : FitType()
}

@Serializable
data class Identifier(
    val type: FitType,
    val manufacturer: String? = EMPTY,
    val product: Int = 0,
    @SerialName("serial_number")
    val serial: String = EMPTY,
    @SerialName("time_created")
    val created: Long = 0L,
)

@Serializable
data class Sport(
    val sport: SportType = SportType.Cycling,
    @SerialName("sub_sport")
    val sub: Int = 0,
)

@Serializable
data class Workout(
    @SerialName("wkr_name")
    val workout: SportType = SportType.Cycling,
)

@Serializable
data class Activity(
    val timestamp: Long,
    @SerialName("total_timer_time")
    val totalDuration: Long = 0L,
    @SerialName("num_sessions")
    val sessionCount: Int = 0,
    val type: String = EMPTY,
    val event: String = EMPTY,
    @SerialName("event_type")
    val eventType: String = EMPTY,
    @SerialName("local_timestamp")
    val localTimestamp: Long = 0L,
)

@Serializable
data class Record(
    val timestamp: String,
    @SerialName("position_lat")
    val latitude: Long = 0L,
    @SerialName("position_long")
    val longitude: Long = 0L,
    val cadence: Int = 0,
    val distance: Long = 0L,
    val power: Int = 0,
    val temperature: Int = 0,
)
