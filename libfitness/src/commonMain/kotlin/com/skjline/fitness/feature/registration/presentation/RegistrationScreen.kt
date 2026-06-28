package com.skjline.fitness.feature.registration.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.skjline.fitness.feature.registration.RegistrationViewModel
import com.skjline.fitness.feature.registration.presentation.view.Completion
import com.skjline.fitness.feature.registration.presentation.view.RegistrationEntry
import com.skjline.fitness.feature.registration.presentation.view.SetUserFitness
import com.skjline.fitness.feature.registration.presentation.view.SetUserProfile
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun RegistrationScreen() {
    val viewModel = koinViewModel<RegistrationViewModel>()

    val regStep = viewModel.task.getTaskProgress().collectAsState("")
    val stateConsumer = viewModel::intakeViewInput

    when (regStep.value) {
        "SetLyncSignIn" -> {}
        "Initial" -> RegistrationEntry(handler = stateConsumer)
        "SetProfile" -> SetUserProfile(handler = stateConsumer)
        "SetFitness" -> SetUserFitness(handler = stateConsumer)
        "Completed" -> Completion(handler = stateConsumer)
        else -> {}
    }
}
