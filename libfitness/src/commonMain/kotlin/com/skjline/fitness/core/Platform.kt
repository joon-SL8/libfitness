package com.skjline.fitness.core

/**
 * Platform Config
 * Each client/platform must implement this interface.
 *
 * may consider in future
 *  - device_os
 *  - device_os_version
 *  - device_memory_usages
 *  - device_active_network
 *  - device_active_network_strength
 *
 * @property name the name of the platform
 */
public interface Platform {
    public val name: String

    public val sdkReportName: String

    public val os: String

    public val buildVersion: String

    public val version: String

    public val httpUserAgent: String

    public val deviceUUID: String

    public val registration: String

    /**
     * When false, T0 local diagnostics and verbose HTTP logging are suppressed.
     */
    public val isDebugBuild: Boolean

    /**
     * Extra JVM/host properties for structured logs (empty on mobile).
     */
    public fun logEnvironmentProperties(): Map<String, String> = emptyMap()

    public fun getDeviceId(): Long
}

expect fun getPlatform(): Platform
