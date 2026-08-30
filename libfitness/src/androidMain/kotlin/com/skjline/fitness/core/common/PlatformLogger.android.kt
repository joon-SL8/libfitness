package com.skjline.fitness.core.common

import android.util.Log

internal actual object PlatformLogger {
    private const val TAG: String = "EntrySDK"

    actual fun log(message: String) {
        Log.d(TAG, message)
    }
}
