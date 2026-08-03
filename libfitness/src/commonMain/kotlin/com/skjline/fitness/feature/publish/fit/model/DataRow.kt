package com.skjline.fitness.feature.publish.fit.model

import kotlinx.serialization.Serializable
import kotlin.experimental.ExperimentalObjCName
import kotlin.native.ObjCName

@OptIn(ExperimentalObjCName::class)
@Serializable
@ObjCName(swiftName = "NSDataRow")
data class DataRow(
    val id: Long = 0L,
    val lat: Long = 0L,
    val long: Long = 0L,
    val power: Int = 0,
    val heartRate: Int = 0,
    val cadence: Int = 0,
    val speed: Int = 0,
)
