package com.skjline.fitness.core.model.generic

data class User (
    val id: Long,
    val username: String,
    val firstname: String,
    val lastname: String,
    var unit: Unit = Metric(),
    var ftp: Int = 0,
    var weight: Float = 0.0f,
    var isStravaAuthorized: Boolean = false
)
