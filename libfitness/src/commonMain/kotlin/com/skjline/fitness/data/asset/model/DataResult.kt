package com.skjline.fitness.data.asset.model

import com.skjline.fitness.core.model.workout.Session
import com.skjline.fitness.core.model.workout.SessionEntry

interface DataResult

data class ValidateCredentialResult(
    val validationResult: ValidationResult
) : DataResult

data object UpdateCredentialResult : DataResult

data object UpdateUserDataResult : DataResult

data object UpdateFitnessResult : DataResult

data object UpdateAthleteDataResult : DataResult

data object UpdateCustomDataResult : DataResult

data class GetDataResult(val data: String) : DataResult

data class UpdateSessionResult(val id: Long) : DataResult

data class GetSessionResult(val sessions: List<Session>) : DataResult

data class GetSessionEntriesResult(val entries: List<SessionEntry>) : DataResult
