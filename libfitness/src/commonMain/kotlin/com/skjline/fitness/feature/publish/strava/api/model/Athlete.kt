package com.skjline.fitness.feature.publish.strava.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Athlete(
    val id: Long = 0,
    val username: String = "",
    val firstname: String = "",
    val lastname: String = "",
    val sex: String? = null, // "M" or "F"
    val summit: Boolean? = false,
    @SerialName("measurement_preference")
    val unit: String? = "meters", // "feet" or "meters"
    val ftp: Int? = 0,
    val weight: Float? = 0.0f,
)