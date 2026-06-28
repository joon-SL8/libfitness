package com.skjline.fitness.presentation.session.navigation

import cafe.adriel.voyager.core.registry.screenModule
import com.skjline.fitness.presentation.session.activity.SessionActivityScreen
import com.skjline.fitness.presentation.session.device.DeviceSelectionScreen

val sessionScreenModule = screenModule {
    register<SessionScreen.ActivityScreen> {
        SessionActivityScreen(it.path)
    }
    register<SessionScreen.DeviceScreen> {
        DeviceSelectionScreen(it.path)
    }
}