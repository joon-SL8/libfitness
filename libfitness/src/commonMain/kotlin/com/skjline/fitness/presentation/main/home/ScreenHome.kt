package com.skjline.fitness.presentation.main.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
//import com.skjline.fitness.presentation.main.home.view.LogCalendar
import com.skjline.fitness.resources.Res
import com.skjline.fitness.resources.app_name
import com.skjline.fitness.resources.explore_train_plan_fragment_label
import org.jetbrains.compose.resources.stringResource
import kotlin.random.Random

class ScreenHome : Screen {

    override val key: ScreenKey
        get() = super.key + "${Random.nextDouble(Double.MIN_VALUE, Double.MAX_VALUE)}"

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val description = stringResource(Res.string.explore_train_plan_fragment_label)

        val greeting = "Welcome to ${stringResource(Res.string.app_name)}"
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(text = greeting)
//            LogCalendar()
        }
    }
}