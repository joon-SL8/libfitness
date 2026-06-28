package com.skjline.fitness.presentation.main.plan

import Launcher
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.skjline.fitness.presentation.SessionRoute
import com.skjline.fitness.injection.AppComponent
import com.skjline.fitness.data.asset.model.AssetProperty
import com.skjline.fitness.data.asset.useCase.AssetPathProviderUseCase
import com.skjline.fitness.presentation.main.plan.view.TrainingPlanListView
import com.skjline.fitness.presentation.shared.style.bg_decorator_color
import org.koin.core.component.get
import kotlin.random.Random

class ScreenWorkout : Screen {

    override val key: ScreenKey
        get() = super.key + "${Random.nextDouble(Double.MIN_VALUE, Double.MAX_VALUE)}"

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        val useCase = AssetPathProviderUseCase()
        val sessionLauncher = AppComponent.get<Launcher>()

        val path = remember { mutableListOf("training") }
        val workouts = remember { mutableStateOf(emptyList<AssetProperty>()) }

        // todo: move to view model
        fun updateTrainingPlans() {
            val result = useCase.invoke(path.joinToString("/"))
            println("traversing useCase result: ${result.size}")
            workouts.value = if (result.isNotEmpty()) {
                listOf(AssetProperty(AssetProperty.Type.Dir, "..")) + result
            } else {
                emptyList()
            }
        }

        LaunchedEffect(true) {
            updateTrainingPlans()
        }

        TrainingPlanListView(
            modifier = Modifier.fillMaxSize().border(width = 1.dp, color = bg_decorator_color),
            content = workouts.value,
        ) { asset ->
            // todo: move to view model
            when (asset.type) {
                AssetProperty.Type.Dir -> {
                    if ((asset.data as? String) == ".." && path.size > 1) {
                        path.removeAt(path.size - 1)
                    } else {
                        path.add(asset.data.toString())
                    }
                    println("traversing path: ${path.joinToString("/")}")
                    updateTrainingPlans()
                }

                AssetProperty.Type.File -> {
                    val file = path.joinToString("/") + "/" + asset.data.toString()
                    println("launching new activity with: $file")
                    sessionLauncher.launch(SessionRoute(file))
                }

                else -> { /* do nothing */
                }
            }
        }
    }
}