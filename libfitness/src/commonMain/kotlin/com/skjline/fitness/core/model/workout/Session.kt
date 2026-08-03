package com.skjline.fitness.core.model.workout

import com.skjline.fitness.core.model.generic.Const.Companion.EMPTY
import kotlinx.serialization.Serializable

@Serializable
public data class Session(
    val id: Long = 0L,
    val name: String = EMPTY,
    val description: String = EMPTY,
    val sessionDate: Long = 0L,
    val duration: Long = 0L,
    val mrcFilename: String = EMPTY,
    val mrcFilepath: String = EMPTY,
    val sessionPublished: Long = 0L,
    val sessionFilename: String = EMPTY,
)
