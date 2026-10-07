package com.skjline.fitness.data.storage.usecase

import com.skjline.fitness.data.asset.model.ValidateCredentialResult
import com.skjline.fitness.data.asset.model.ValidationResult
import com.skjline.fitness.data.storage.Constants.Companion.KEY_CREDENTIAL
import com.skjline.fitness.data.storage.input.CredentialDataInput
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ValidateUserCredentialUseCase : BaseDataUseCase<CredentialDataInput, ValidateCredentialResult>() {
    private var index = 0

    private val _onValidated = MutableStateFlow(false)
    val onValidated: StateFlow<Boolean> = _onValidated

    override suspend operator fun invoke(input: CredentialDataInput): ValidateCredentialResult {
        if (input.username.isEmpty() || input.password.isEmpty()) {
            return ValidateCredentialResult(ValidationResult.Error)
        }

        storage.getUserProfile(KEY_CREDENTIAL)?.let { credential ->
            val persistedCredential = credential.data_.split("::")
            val isProperCredentialFormat = persistedCredential.size == 2

            val result = isProperCredentialFormat &&
                    persistedCredential[0] == input.username &&
                    persistedCredential[1] == input.password

            _onValidated.emit(result)

            if (result) {
                return ValidateCredentialResult(ValidationResult.Valid)
            }
        }
        return ValidateCredentialResult(ValidationResult.Invalid)
    }
}