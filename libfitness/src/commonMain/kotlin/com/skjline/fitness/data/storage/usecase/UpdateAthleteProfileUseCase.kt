package com.skjline.fitness.data.storage.usecase

import com.skjline.fitness.data.asset.model.UpdateAthleteDataResult
import com.skjline.fitness.data.storage.Constants.Companion.PROFILE_KEY_AGE
import com.skjline.fitness.data.storage.Constants.Companion.PROFILE_KEY_FTP
import com.skjline.fitness.data.storage.Constants.Companion.PROFILE_KEY_NAME
import com.skjline.fitness.data.storage.Constants.Companion.PROFILE_KEY_WEIGHT
import com.skjline.fitness.data.storage.input.AthleteProfileDataInput
import kotlinx.coroutines.withContext

class UpdateAthleteProfileUseCase : BaseDataUseCase<AthleteProfileDataInput, UpdateAthleteDataResult>() {
    override suspend operator fun invoke(input: AthleteProfileDataInput): UpdateAthleteDataResult {
        withContext(dispatcherProvider.io) {
            storage.insertOrUpdateUserProfile(PROFILE_KEY_NAME, input.name)
            storage.insertOrUpdateUserProfile(PROFILE_KEY_AGE, "${input.age}")
            storage.insertOrUpdateUserProfile(PROFILE_KEY_FTP, "${input.ftp}")
            storage.insertOrUpdateUserProfile(PROFILE_KEY_WEIGHT, "${input.weight}")
        }

        return UpdateAthleteDataResult
    }
}
