package com.skjline.fitness.core.common

import com.skjline.fitness.core.model.generic.Const.Companion.EMPTY
import kotlin.time.Clock.System.now

internal object LocalDiagnostics {
    private const val PREFIX: String = "LocalDiagnostics"

    public fun trace(
        topic: String,
        detail: String = EMPTY,
    ) {
        val ts: Long = now().toEpochMilliseconds()
        val prefix: String = "$ts >>>>> $PREFIX: $topic"
        val tracing: String = detail.takeIf { it.isNotEmpty() }?.let { " | $detail" }.orEmpty()
        val message: String = prefix + tracing

        PlatformLogger.log(message)
    }
}
