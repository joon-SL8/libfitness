package com.skjline.fitness.core

import com.skjline.fitness.core.utils.Const.Companion.EMPTY
import platform.UIKit.UIDevice

class IOSPlatform : Platform {
    override val name: String =
        UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion

    override val os: String = "iOS"
    override val version: String = EMPTY
    override val deviceUUID: String = EMPTY
    override val registration: String = EMPTY
    override val buildVersion: String = EMPTY
    override val sdkReportName: String = EMPTY
    override val httpUserAgent: String = EMPTY

    override val isDebugBuild: Boolean = false

    override fun getDeviceId(): Long = 0L
}

actual fun getPlatform(): Platform = IOSPlatform()
