package com.skjline.fitness.data.storage.usecase

import com.skjline.fitness.data.asset.model.UpdateUserProfileResult
import com.skjline.fitness.data.storage.Constants.Companion.PROFILE_KEY_AGE
import com.skjline.fitness.data.storage.Constants.Companion.PROFILE_KEY_NAME
import com.skjline.fitness.data.storage.UserProfileEntity
import com.skjline.fitness.data.storage.input.UserProfileInput
import kotlin.time.Clock.System.now
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
class UpdateUserProfileUseCase : BaseDataUseCase<UserProfileInput, UpdateUserProfileResult>() {
    override suspend operator fun invoke(input: UserProfileInput): UpdateUserProfileResult {
        val contents = storage.database.userProfileQueries
            .getAll().executeAsList()
        val index = contents.size

        insertOrUpdateData(index + 1, input.name, PROFILE_KEY_NAME, contents)
        insertOrUpdateData(index + 2, "${input.age}", PROFILE_KEY_AGE, contents)

        return UpdateUserProfileResult
    }

    private suspend fun insertOrUpdateData(index: Int, data: String, type: String, contents: List<UserProfileEntity>) {
        contents.findLast {
            it.name == type
        }?.let { fitness ->
            storage.database.userProfileQueries
                .updateData(id = fitness.id, data = data)
        } ?: run {
            storage.database.userProfileQueries.insert(
                id = index.toLong(),
                name = type,
                data_ = data,
                updated = now().epochSeconds,
            )
        }
    }
}
