package com.skjline.fitness.data.storage.usecase

import com.skjline.fitness.data.asset.model.UpdateUserDataResult
import com.skjline.fitness.data.storage.Constants.Companion.PROFILE_KEY_AGE
import com.skjline.fitness.data.storage.Constants.Companion.PROFILE_KEY_NAME
import com.skjline.fitness.data.storage.input.UserProfileInput

class UpdateUserProfileUseCase : BaseDataUseCase<UserProfileInput, UpdateUserDataResult>() {
    override suspend operator fun invoke(input: UserProfileInput): UpdateUserDataResult {
        storage.insertOrUpdateUserProfile(PROFILE_KEY_NAME, input.name)
        storage.insertOrUpdateUserProfile(PROFILE_KEY_AGE, input.age.toString())

        return UpdateUserDataResult
    }
}
