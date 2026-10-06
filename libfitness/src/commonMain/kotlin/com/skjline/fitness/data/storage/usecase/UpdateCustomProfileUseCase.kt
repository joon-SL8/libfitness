package com.skjline.fitness.data.storage.usecase

import com.skjline.fitness.data.asset.model.UpdateCustomDataResult
import com.skjline.fitness.data.storage.Constants.Companion.PROFILE_KEY_PRE
import com.skjline.fitness.data.storage.input.UpdateCustomInput

class UpdateCustomProfileUseCase : BaseDataUseCase<UpdateCustomInput, UpdateCustomDataResult>() {
    override suspend operator fun invoke(input: UpdateCustomInput): UpdateCustomDataResult {
        println("Inserting or Updating ${input.key} for $input")
        try {
            insertOrUpdateData("$PROFILE_KEY_PRE::${input.key}", input.data)
        } catch (e: Exception) {
            println("Exception while trying to insert $input")
        }
        return UpdateCustomDataResult
    }
}
