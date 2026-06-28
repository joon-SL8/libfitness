package com.skjline.fitness.presentation.session

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.skjline.fitness.presentation.session.device.DeviceSelectionScreen

@Composable
fun SessionScreen(
    path: String? = null
) {
    MaterialTheme {
        Navigator(
            screen = DeviceSelectionScreen(path),
        ) { navigation ->
            SlideTransition(navigation)
        }
    }
}