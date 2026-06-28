package com.skjline.fitness.core.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

class DispatcherProvider {
    val main = Dispatchers.Main
    val default = Dispatchers.Default
    val io = Dispatchers.IO
    val immediate = main.immediate
}
