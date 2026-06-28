package com.skjline.fitness.core.model.workout

import androidx.compose.ui.graphics.Color
import com.skjline.fitness.core.model.generic.Const.Companion.EMPTY
import com.skjline.fitness.presentation.shared.toBitmap
import kotlinx.serialization.Serializable

@Serializable
data class MrcCourseText(
    val first: String = EMPTY,
    val second: String = EMPTY,
    val third: String = EMPTY,
)

@Serializable
data class MrcCourse(
    val version: String = EMPTY,
    val units: String = EMPTY,
    val description: String = EMPTY,
    val filename: String = EMPTY,
    val shortFileName: String = EMPTY,
    val course: List<Pair<Float, Float>> = emptyList(),
    val text: List<MrcCourseText> = emptyList(),
) {
    fun getAsImage(color: Color) = course.toBitmap(
        color = color,
        canvasWidth = 800f,
        canvasHeight = 200f
    )

    fun totalCourseTime() = course.last().first
}
