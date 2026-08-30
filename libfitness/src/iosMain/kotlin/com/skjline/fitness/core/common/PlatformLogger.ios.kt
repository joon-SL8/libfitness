package com.skjline.fitness.core.common

import platform.Foundation.NSLog

internal actual object PlatformLogger {
    actual fun log(message: String) {
        NSLog("%@", message)
    }
}
