package com.skjline.fitness.feature.registration.task

import com.skjline.fitness.core.utils.DispatcherProvider
import com.skjline.fitness.data.storage.Constants.Companion.KEY_CREDENTIAL
import com.skjline.fitness.data.storage.StorageDatabase
import com.skjline.fitness.feature.registration.model.ApplyCredential
import com.skjline.fitness.feature.registration.model.CredentialInput
import com.skjline.fitness.feature.registration.model.Input
import com.skjline.fitness.feature.registration.model.RegStep
import com.skjline.fitness.injection.AppComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.get

class RegistrationTask {
    private val storage = AppComponent.get<StorageDatabase>()

    private val dispatcher: DispatcherProvider by lazy {
        AppComponent.get<DispatcherProvider>()
    }

    private val status = MutableStateFlow<RegStep>(ApplyCredential)

    fun getTaskProgress(): Flow<RegStep> = status

    fun initialize() {
        storage.initialize()
   }

    fun updateStatus(step: RegStep) {
        println("update step to: $step")
        CoroutineScope(dispatcher.io).launch {
            status.emit(step)
        }
    }


    fun validateInputData(input: Input): Boolean = when (input) {
        is CredentialInput -> {
            val contents = storage.database.userProfileQueries
                .getAll().executeAsList()

            val credential = contents.firstOrNull {
                it.name == KEY_CREDENTIAL
            }?.data_.toString().split("::")

            val result = credential.size == 2 &&
                    credential[0] == input.username &&
                    credential[1] == input.password
            result
        }

        else -> true
    }
}
