package com.skjline.fitness.data.asset.model.workout

import com.skjline.fitness.core.model.generic.Const.Companion.EMPTY
import kotlinx.serialization.Serializable

@Serializable
data class VEvent(
    val uid: String = EMPTY,
    val timestamp: Long = -1L,
    val summary: String = EMPTY,
    val description: String = EMPTY,
    val url: String = EMPTY,
    val category: String = EMPTY,
)

@Serializable
data class VCalendar(
    val version: String = EMPTY,
    val scale: String = EMPTY,
    val prodID: String = EMPTY,
    val method: String = EMPTY,
    val events: List<VEvent> = emptyList(),
)
