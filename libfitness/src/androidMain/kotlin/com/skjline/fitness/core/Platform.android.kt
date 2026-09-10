package com.skjline.fitness.core

import android.os.Build
import com.skjline.fitness.core.utils.Const.Companion.EMPTY

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"

    override val os: String = "Android"
    override val version: String = EMPTY
    override val deviceUUID: String = EMPTY
    override val registration: String = EMPTY
    override val buildVersion: String = EMPTY
    override val sdkReportName: String = EMPTY
    override val httpUserAgent: String = EMPTY

    override val isDebugBuild: Boolean = false

    override fun getDeviceId(): Long = 0L
}

actual fun getPlatform(): Platform = AndroidPlatform()
