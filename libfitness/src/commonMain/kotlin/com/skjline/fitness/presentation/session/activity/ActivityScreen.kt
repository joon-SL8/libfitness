package com.skjline.fitness.presentation.session.activity

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import com.skjline.fitness.core.model.generic.PacketType
import com.skjline.fitness.core.utils.parseFilenameFromPath
import com.skjline.fitness.core.model.workout.MrcCourse
import com.skjline.fitness.feature.collect.model.CollectorState
import com.skjline.fitness.feature.collect.service.DataCollectionService
import com.skjline.fitness.core.model.generic.DataTimer
import com.skjline.fitness.core.model.generic.Stop
import com.skjline.fitness.core.model.packet.TotalTime
import com.skjline.fitness.feature.arrange.useCase.DeviceConnectorUseCase
import com.skjline.fitness.presentation.session.activity.view.ActivityControlView
import com.skjline.fitness.presentation.session.activity.view.TelemetryCollectionStaticView
import com.skjline.fitness.presentation.session.activity.view.WorkloadControllerView
import com.skjline.fitness.presentation.session.activity.view.WorkoutProgressView
import com.skjline.fitness.presentation.shared.style.Dimens

@Composable
fun ActivityScreen(
    sessionStatus: CollectorState,
    dataCollectionService: DataCollectionService,
    devices: List<String> = emptyList(),
    mrcCourse: MrcCourse,
    imageWorkoutGraph: ImageBitmap?,
    deviceConnectorUseCase: DeviceConnectorUseCase = DeviceConnectorUseCase(),
) {
    @Suppress("UNCHECKED_CAST")
    var timeProvider = dataCollectionService.getProviderOf(PacketType.TotalTime)
            as? DataTimer<TotalTime>

    var totalTimeInSec by remember { mutableFloatStateOf(0f) }

    var services by remember { mutableStateOf(listOf<PacketType>()) }

    LaunchedEffect(true) {
        deviceConnectorUseCase.invoke(devices).forEach { dataCollector ->
            dataCollectionService.addCollector(dataCollector)
        }
        services = dataCollectionService.getServiceList()

        totalTimeInSec = mrcCourse.course.lastOrNull()?.first ?: 0f
        println("total time (sec): $totalTimeInSec")
    }

    DisposableEffect(true) {
        onDispose {
            timeProvider?.request(Stop)
            timeProvider = null
        }
    }

    Column(
        modifier = Modifier.padding(horizontal = Dimens.Padding.Normal.asDP()).fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        val startItemModifier = Modifier.align(Alignment.Start)
        val displayName = mrcCourse.shortFileName.parseFilenameFromPath()
        Text(
            text = displayName,
            modifier = startItemModifier,
            style = MaterialTheme.typography.headlineSmall,
        )

        ActivityControlView(
            startItemModifier = startItemModifier,
            course = mrcCourse,

            sessionStatus = sessionStatus,
            dataCollectionService = dataCollectionService,
        )

        TelemetryCollectionStaticView(
            modifier = Modifier,
            availableServices = services,
            collectionService = dataCollectionService,
        )

        imageWorkoutGraph?.let { image ->
            WorkoutProgressView(
                modifier = Modifier,
                content = image,
                totalTimeInSec = totalTimeInSec,
                hr = emptyList(),
                power = emptyList(),
                provider = timeProvider,
            )
        }

        WorkloadControllerView(
            modifier = startItemModifier,
            dataCollectionService
        )
    }
}
