package com.skjline.fitness.data.storage.usecase

import com.skjline.fitness.core.utils.DispatcherProvider
import com.skjline.fitness.data.asset.model.UpdateFitnessResult
import com.skjline.fitness.data.storage.Constants.Companion.PROFILE_KEY_FTP
import com.skjline.fitness.data.storage.Constants.Companion.PROFILE_KEY_WEIGHT
import com.skjline.fitness.data.storage.input.FitnessInput
import com.skjline.fitness.injection.AppComponent
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.inject

class UpdateUserFitnessUseCase : BaseDataUseCase<FitnessInput, UpdateFitnessResult>() {
    private val dispatcher: DispatcherProvider by AppComponent.inject()

    override suspend operator fun invoke(input: FitnessInput): UpdateFitnessResult {
        val ftp = input.ftp.toString()
        val wt = input.weight.toString()
        withContext(dispatcher.io) {
            listOf(
                launch { storage.insertOrUpdateUserProfile(PROFILE_KEY_FTP, ftp) },
                launch { storage.insertOrUpdateUserProfile(PROFILE_KEY_WEIGHT, wt) }
            ).joinAll()
        }

        return UpdateFitnessResult
    }
}
