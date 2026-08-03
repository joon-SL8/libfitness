package com.skjline.fitness.core.model.workout

import com.skjline.fitness.core.model.generic.Const.Companion.EMPTY
import kotlinx.serialization.Serializable

@Serializable
public data class SessionEntry(
    val id: Long = 0,
    val session: Long = 0,
    val timestamp: Long = 0,
    val name: String = EMPTY,
    val description: String = EMPTY,
    val start: String = EMPTY,
    val duration: Long = 0,
    val power: Long = 0,
    val heart: Long = 0,
    val speed: Long = 0,
    val cadence: Long = 0,
)
