package com.skjline.fitness.data.asset.model.workout

import kotlinx.serialization.Serializable

@Serializable
data class Cue(
    val timestamp: Float,
    val message: String,
    val duration: Int,
)

@Serializable
data class Course(
    val timestamp: Float,
    val percentile: Int,
)

enum class WorkoutFileType(val ext: String) {
    ERG(ext = "erg"), MRC(ext = "mrc"), ZWIFT(ext = "zwo"), FIT(ext = "fit")
}
