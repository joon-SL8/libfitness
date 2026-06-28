package com.skjline.fitness.presentation.session.activity

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.skjline.fitness.core.utils.DispatcherProvider
import com.skjline.fitness.core.utils.parseFilenameFromPath
import com.skjline.fitness.feature.collect.service.DataCollectionService
import com.skjline.fitness.feature.collect.useCase.GenerateActivitySessionUseCase
import com.skjline.fitness.feature.publish.fit.model.DataUploadReady
import com.skjline.fitness.feature.publish.strava.usecase.PublishSessionActivityUseCase
import com.skjline.fitness.injection.AppComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.core.component.get

@Composable
fun ActivityPublishing(
    path: String,
    dataCollectionService: DataCollectionService,
    generateSessionActivityUseCase: GenerateActivitySessionUseCase,
) {
    val dispatcherProvider = AppComponent.get<DispatcherProvider>()
    val job = dispatcherProvider.io + Job()

    var uploadState = remember { mutableStateOf("Initialized") }

    LaunchedEffect(path) {
        // when completing creating a fit file, upload it to tracking service, e.g. Strava
        CoroutineScope(job).launch {
            dataCollectionService.status.collectLatest { item ->
                val status = (item as? DataUploadReady) ?: return@collectLatest

                val filename = status.param.filename
                val publishSessionActivityUseCase = PublishSessionActivityUseCase(
                    title = path.parseFilenameFromPath(),
                    startTime = dataCollectionService.startedAt,
                    duration = dataCollectionService.duration,
                    fitFilename = filename,
                )

                CoroutineScope(job).launch {
                    publishSessionActivityUseCase.status.collectLatest { result ->
                        val complete = when (result) {
                            is PublishSessionActivityUseCase.PublishResult.Success -> {
                                println("activity successfully published")
                                true
                            }

                            is PublishSessionActivityUseCase.PublishResult.Failed -> {
                                println("activity publish failed")
                                false
                            }

                            is PublishSessionActivityUseCase.PublishResult.InProgress -> {
                                uploadState.value = "fit file upload in-progress"
                                return@collectLatest
                            }

                            else -> {
                                return@collectLatest
                            }
                        }

//                        dataCollectionService.onUploadCompleted()
                        uploadState.value = if (complete) {
                            "fit file successfully uploaded"
                        } else {
                            "fit file upload failed"
                        }
//                        dataCollectionService.onUploadCompleted()
                    }
                }

                uploadState.value = "Start Uploading"
                publishSessionActivityUseCase.invoke()
            }
        }

        // upon completion of an workout activity, generate a fit file
        CoroutineScope(job).launch {
            uploadState.value = "Generating the activity fit file"
            generateSessionActivityUseCase.invoke()
        }
    }

    Text(
        modifier = Modifier.fillMaxWidth(),
        text = uploadState.value,
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.headlineSmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
    )
}
