package com.skjline.fitness.presentation.session.navigation

import cafe.adriel.voyager.core.registry.ScreenProvider

sealed class SessionScreen: ScreenProvider {
    data class ActivityScreen(val path: String): SessionScreen()
    data class DeviceScreen(val path: String): SessionScreen()
}