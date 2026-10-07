package com.skjline.fitness.data.storage.usecase

import com.skjline.fitness.data.asset.model.UpdateCredentialResult
import com.skjline.fitness.data.storage.Constants.Companion.KEY_CREDENTIAL
import com.skjline.fitness.data.storage.input.CredentialDataInput

class UpdateCreditUseCase : BaseDataUseCase<CredentialDataInput, UpdateCredentialResult>() {
    override suspend operator fun invoke(input: CredentialDataInput): UpdateCredentialResult {
        val data = "${input.username}::${input.password}"
        val contents = storage.insertOrUpdateUserProfile(KEY_CREDENTIAL, data)
        return UpdateCredentialResult
    }
}