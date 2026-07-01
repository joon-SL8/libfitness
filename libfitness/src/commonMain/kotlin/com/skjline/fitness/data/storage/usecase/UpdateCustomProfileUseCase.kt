package com.skjline.fitness.data.storage.usecase

import com.skjline.fitness.data.asset.model.UpdateCustomDataResult
import com.skjline.fitness.data.storage.Constants.Companion.PROFILE_KEY_PRE
import com.skjline.fitness.data.storage.input.UpdateCustomInput
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
class UpdateCustomProfileUseCase : BaseDataUseCase<UpdateCustomInput, UpdateCustomDataResult>() {
    override suspend operator fun invoke(input: UpdateCustomInput): UpdateCustomDataResult {
        val contents = storage.database.userProfileQueries
            .getAll().executeAsList()
        val index = contents.size

        insertOrUpdateData(index + 1, input.data, "$PROFILE_KEY_PRE::${input.key}", contents)

        return UpdateCustomDataResult
    }
}
