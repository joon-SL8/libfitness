package com.skjline.fitness.core.common

/**
 * Platform-native console output for T0 local diagnostics (debug builds only).
 */
internal expect object PlatformLogger {
    public fun log(message: String)
}
