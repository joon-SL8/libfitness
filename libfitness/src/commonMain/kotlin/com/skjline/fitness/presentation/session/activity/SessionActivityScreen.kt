package com.skjline.fitness.presentation.session.activity

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.skjline.fitness.core.model.generic.Const.Companion.EMPTY
import com.skjline.fitness.core.model.workout.MrcCourse
import com.skjline.fitness.data.asset.useCase.AssetFileParseUseCase
import com.skjline.fitness.data.asset.useCase.AssetFilePathUseCase
import com.skjline.fitness.feature.collect.model.Initializing
import com.skjline.fitness.feature.collect.model.SessionStop
import com.skjline.fitness.feature.collect.service.DataCollectionService
import com.skjline.fitness.feature.collect.useCase.GenerateActivitySessionUseCase
import com.skjline.fitness.feature.arrange.useCase.DeviceConnectorUseCase
import com.skjline.fitness.injection.AppComponent
import com.skjline.fitness.presentation.shared.ScreenTopBar
import com.skjline.fitness.presentation.shared.style.Blue40
import com.skjline.fitness.resources.Res
import com.skjline.fitness.resources.todays_training
import kotlinx.coroutines.flow.collectLatest
import org.jetbrains.compose.resources.stringResource
import org.koin.core.component.get
import kotlin.random.Random

class SessionActivityScreen(
    private val path: String = EMPTY,
    private val devices: List<String> = emptyList(),
    private val deviceConnectorUseCase: DeviceConnectorUseCase = DeviceConnectorUseCase(),
) : Screen {

    override val key: ScreenKey
        get() = super.key + "${Random.nextDouble(Double.MIN_VALUE, Double.MAX_VALUE)}"

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        val dataCollectionService = AppComponent.get<DataCollectionService>()
        val sessionStatus = dataCollectionService.status
            .collectAsState(Initializing)

        var screenLabel = remember { "Training" }
        var imageWorkoutGraph: ImageBitmap? by remember { mutableStateOf(null) }
        var mrc: MrcCourse? by remember { mutableStateOf(null) }
        val assetFileProvider = AssetFilePathUseCase()

        LaunchedEffect(screenLabel) {
            if (path.isNotEmpty()) {
                assetFileProvider(path).collectLatest { content ->
                    val file = path.substring(path.lastIndexOf("/") + 1)

                    val parser = AssetFileParseUseCase(file, content.split("\n"))
                    parser()?.let { data ->
                        mrc = data
                    }

                    imageWorkoutGraph = mrc?.getAsImage(Blue40)
                }
            }
        }

        DisposableEffect(true) {
            onDispose {
                dataCollectionService.getServiceList().forEach {

                }
            }
        }

        Column(modifier = Modifier.fillMaxSize()) {
            ScreenTopBar(
                label = screenLabel,
            ) {
                navigator.pop()
            }

            if (sessionStatus.value is SessionStop) {
                screenLabel = "Publishing"
                ActivityPublishing(
                    path = path,
                    dataCollectionService = dataCollectionService,
                    generateSessionActivityUseCase = GenerateActivitySessionUseCase(
                        dataCollectionService
                    ),
                )
            } else {
                screenLabel = stringResource(resource = Res.string.todays_training)
                mrc?.let { course ->
                    ActivityScreen(
                        sessionStatus = sessionStatus.value,
                        dataCollectionService = dataCollectionService,
                        devices = devices,
                        mrcCourse = course,
                        imageWorkoutGraph = imageWorkoutGraph,
                        deviceConnectorUseCase = deviceConnectorUseCase,
                    )
                }
            }
        }
    }
}
