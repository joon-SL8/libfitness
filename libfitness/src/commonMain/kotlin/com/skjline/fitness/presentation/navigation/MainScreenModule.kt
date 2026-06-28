package com.skjline.fitness.presentation.navigation

import cafe.adriel.voyager.core.registry.screenModule
import com.skjline.fitness.presentation.main.home.ScreenHome
import com.skjline.fitness.presentation.main.profile.ScreenProfile
import com.skjline.fitness.presentation.main.plan.ScreenWorkout

val mainScreenModule = screenModule {
    register<MainScreen.Home> { ScreenHome() }
    register<MainScreen.Workout> { ScreenWorkout() }
    register<MainScreen.Profile> { ScreenProfile() }
}
