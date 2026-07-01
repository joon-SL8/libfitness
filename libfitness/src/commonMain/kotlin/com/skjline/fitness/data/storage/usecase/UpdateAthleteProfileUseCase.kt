package com.skjline.fitness.data.storage.usecase

import com.skjline.fitness.data.asset.model.UpdateAthleteDataResult
import com.skjline.fitness.data.storage.Constants.Companion.PROFILE_KEY_AGE
import com.skjline.fitness.data.storage.Constants.Companion.PROFILE_KEY_FTP
import com.skjline.fitness.data.storage.Constants.Companion.PROFILE_KEY_NAME
import com.skjline.fitness.data.storage.Constants.Companion.PROFILE_KEY_WEIGHT
import com.skjline.fitness.data.storage.input.AthleteProfileDataInput
import kotlinx.coroutines.withContext
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
class UpdateAthleteProfileUseCase : BaseDataUseCase<AthleteProfileDataInput, UpdateAthleteDataResult>() {
    override suspend operator fun invoke(input: AthleteProfileDataInput): UpdateAthleteDataResult {
        val contents = storage.database.userProfileQueries.getAll().executeAsList()
        val index = contents.size
        withContext(dispatcherProvider.io) {
            insertOrUpdateData(index + 1, input.name, PROFILE_KEY_NAME, contents)
            insertOrUpdateData(index + 2, "${input.age}", PROFILE_KEY_AGE, contents)
            insertOrUpdateData(index + 3, "${input.ftp}", PROFILE_KEY_FTP, contents)
            insertOrUpdateData(index + 4, "${input.weight}", PROFILE_KEY_WEIGHT, contents)
        }

        return UpdateAthleteDataResult
    }
}
