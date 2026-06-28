package com.skjline.fitness.presentation.navigation

import cafe.adriel.voyager.core.registry.ScreenProvider

sealed interface MainScreen: ScreenProvider {
    data object Home: MainScreen
    data object Workout: MainScreen
    data object Profile: MainScreen
}
