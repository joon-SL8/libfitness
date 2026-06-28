package com.skjline.fitness.core.utils

fun String.parseFilenameFromPath() =
    if (contains("/") || contains(".")) {
        substring(
            (lastIndexOf("/") + 1).coerceAtLeast(0),
            lastIndexOf(".").coerceAtMost(length)
        )
    } else {
        this
    }

fun String.toIntDefault(default: Int = 0) =
    this.toIntOrNull() ?: default

