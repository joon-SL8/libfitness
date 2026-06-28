@file:OptIn(ExperimentalTime::class)

package com.skjline.fitness.data.storage.usecase

import com.skjline.fitness.data.asset.model.UpdateCredentialResult
import com.skjline.fitness.data.storage.Constants.Companion.KEY_CREDENTIAL
import com.skjline.fitness.data.storage.input.CredentialDataInput
import kotlin.time.Clock.System.now
import kotlin.time.ExperimentalTime

class UpdateCreditUseCase : BaseDataUseCase<CredentialDataInput, UpdateCredentialResult>() {
    override suspend operator fun invoke(input: CredentialDataInput): UpdateCredentialResult {
        val contents = storage.database.userProfileQueries
            .getAll().executeAsList()

        contents.findLast {
            it.name == KEY_CREDENTIAL
        }?.let { credential ->
            storage.database.userProfileQueries
                .updateData(id = credential.id, data = "${input.username}::${input.password}")
        } ?: run {
            storage.database.userProfileQueries.insert(
                id = (contents.size + 1).toLong(),
                name = KEY_CREDENTIAL,
                data_ = "${input.username}::${input.password}",
                updated = now().epochSeconds,
            )
        }

        return UpdateCredentialResult
    }
}