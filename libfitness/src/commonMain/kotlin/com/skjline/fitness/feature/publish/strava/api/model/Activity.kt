package com.skjline.fitness.feature.publish.strava.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Activity(
    val id: Long,
    val name: String,
    val type: String,
    @SerialName("sport_type") val sports: String,
    @SerialName("start_date_local") val start: String, // ISO8601 formatted
    @SerialName("elapsed_time") val elapsed: Int, // in sec
)