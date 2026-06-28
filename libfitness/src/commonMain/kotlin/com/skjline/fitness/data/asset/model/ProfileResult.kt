package com.skjline.fitness.data.asset.model

interface ProfileResult

data class ValidateCredentialResult(
    val validationResult: ValidationResult
) : ProfileResult

data object UpdateCredentialResult : ProfileResult

data object UpdateUserProfileResult : ProfileResult

data object UpdateFitnessResult : ProfileResult

data object UpdateAthleteProfileResult : ProfileResult
