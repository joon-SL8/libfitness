package com.skjline.fitness.feature.registration

import Launcher
import androidx.lifecycle.ViewModel
import com.skjline.fitness.core.utils.DispatcherProvider
import com.skjline.fitness.data.asset.model.DataResult
import com.skjline.fitness.data.storage.input.AthleteProfileDataInput
import com.skjline.fitness.data.storage.input.CredentialDataInput
import com.skjline.fitness.data.storage.input.UserProfileInput
import com.skjline.fitness.data.storage.usecase.DataUseCase
import com.skjline.fitness.data.storage.usecase.UpdateAthleteProfileUseCase
import com.skjline.fitness.data.storage.usecase.UpdateCreditUseCase
import com.skjline.fitness.data.storage.usecase.UpdateUserFitnessUseCase
import com.skjline.fitness.data.storage.usecase.UpdateUserProfileUseCase
import com.skjline.fitness.feature.registration.model.*
import com.skjline.fitness.feature.registration.task.RegistrationTask
import com.skjline.fitness.injection.AppComponent
import com.skjline.fitness.presentation.MainRoute
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.core.component.get
import org.koin.core.component.inject

class RegistrationViewModel : ViewModel() {
    private val dispatcher: DispatcherProvider by AppComponent.inject()

    private val launcher by lazy { AppComponent.get<Launcher>() }

    val task: RegistrationTask by lazy { RegistrationTask() }

    fun intakeViewInput(regStep: RegStep, action: Action) {
        println("step: $regStep - action: $action")
        task.updateStatus(regStep)
        when (regStep) {
            SetLyncSignIn -> {}
            ApplyCredential -> consumeInitializeAction(action)
            SetProfile -> consumeUserProfileAction(action)
            SetFitness -> consumeUserFitnessAction(action)
            SetAthleteProfile -> consumeAthleteProfileAction(action)
            Finish -> (action as? Next)?.let { updateInputData(it.input) }
            else -> updateRegistrationStep(route = Route(null, null, regStep = regStep))
        }
    }

    private fun consumeInitializeAction(action: Action) {
        when (action) {
            is Next -> if (task.validateInputData(input = action.input)) {
                launcher.launch(MainRoute())
            }

            is Register -> updateInputData(action.input)
            Exit -> {}
            Cancel -> {}
        }
    }

    private fun consumeUserProfileAction(action: Action) {
        when (action) {
            is Next -> updateInputData(action.input)
            is Register -> {}
            Exit -> {}
            Cancel -> {}
        }
    }

    private fun consumeUserFitnessAction(action: Action) {
        when (action) {
            is Next -> updateInputData(action.input)
            is Register -> {}
            Exit -> {}
            Cancel -> {}
        }
    }

    private fun consumeAthleteProfileAction(action: Action) {
        when (action) {
            is Next -> {}
            is Register -> updateInputData(action.input)
            Exit -> {}
            Cancel -> {}
        }
    }

    private fun validateRegistrationData(input: Input): Boolean = when (input) {
        is CredentialInput -> {
            val format = REGEX_EMAIL.toRegex().matches(input.username)
            format && input.password.isNotBlank()
        }

        is ProfileInput -> {
            input.age in 0..99 && input.name.isNotBlank()
        }

        is FitnessInput -> {
            input.ftp in 0..500 && input.weight in 0..300
        }

        else -> true
    }

    private fun updateInputData(input: Input) {
        if (!validateRegistrationData(input)) {
            println("update validation failed")
            return
        }

        val route = when (input) {
            is CredentialInput -> Route(
                useCase = UpdateCreditUseCase(),
                input = CredentialDataInput(input.username, input.password),
                regStep = SetProfile,
            )

            is ProfileInput -> Route(
                useCase = UpdateUserProfileUseCase(),
                input = UserProfileInput(input.name, input.age),
                regStep = SetFitness
            )

            is FitnessInput -> Route(
                useCase = UpdateUserFitnessUseCase(),
                input = com.skjline.fitness.data.storage.input.FitnessInput(input.ftp, input.weight),
                regStep = Completed,
            )

            is AthleteProfileInput -> Route(
                useCase = UpdateAthleteProfileUseCase(),
                input = AthleteProfileDataInput(input.name, input.age, input.ftp, input.weight),
                regStep = Completed,
            )

            is ProcessComplete -> {
                if (!input.isReg || !input.isSucceed) {
                    return
                }
                CoroutineScope(dispatcher.main).launch {
                    launcher.launch(MainRoute())
                }
                return
            }

            else -> return
        }

        task.updateStatus(route.regStep)
        updateRegistrationStep(route = route)
    }

    private fun <I : com.skjline.fitness.data.storage.input.Input, R : DataResult> updateRegistrationStep(
        route: Route<I, R>
    ) {
        CoroutineScope(dispatcher.io).launch {
            route.useCase?.let { useCase ->
                route.input?.let { input ->
                    useCase.invoke(input)
                }
            }
        }
    }

    internal data class Route<I : com.skjline.fitness.data.storage.input.Input, R : DataResult>(
        val useCase: DataUseCase<I, R>?,
        val input: I?,
        val regStep: RegStep
    )

    companion object {
        const val EXPIRE_COMPLETION_DISPLAY = 3000L

        const val REGEX_EMAIL = "^[\\w\\.]+@(\\w+\\.)+[\\w-]{2,4}"
    }
}
