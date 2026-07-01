package com.skjline.fitness.data.storage.usecase

import com.skjline.fitness.data.asset.model.UpdateUserDataResult
import com.skjline.fitness.data.storage.Constants.Companion.PROFILE_KEY_AGE
import com.skjline.fitness.data.storage.Constants.Companion.PROFILE_KEY_NAME
import com.skjline.fitness.data.storage.input.UserProfileInput
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
class UpdateUserProfileUseCase : BaseDataUseCase<UserProfileInput, UpdateUserDataResult>() {
    override suspend operator fun invoke(input: UserProfileInput): UpdateUserDataResult {
        val contents = storage.database.userProfileQueries.getAll().executeAsList()
        val index = contents.size

        insertOrUpdateData(index + 1, input.name, PROFILE_KEY_NAME, contents)
        insertOrUpdateData(index + 2, "${input.age}", PROFILE_KEY_AGE, contents)

        return UpdateUserDataResult
    }
}
