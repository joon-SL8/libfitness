package com.skjline.fitness.data.storage.input

import com.skjline.fitness.core.model.workout.Session
import com.skjline.fitness.core.model.workout.SessionEntry

interface Input

data class CredentialDataInput(val username: String, val password: String) : Input

data class UserProfileInput(val name: String, val age: Int) : Input

data class FitnessInput(val ftp: Int, val weight: Int) : Input

data class AthleteProfileDataInput(val name: String, val age: Int, val ftp: Int, val weight: Int) : Input

data class UpdateCustomInput(val key: String, val data: String) : Input

data class GetProfileInput(val key: String) : Input

data class InsertSessionInfoInput(val data: Session) : Input

data class UpdateSessionPublishInput(val sessionId: Long, val timestamp: Long) : Input

data class InsertSessionEntryInput(val data: SessionEntry) : Input

data object GetAllInput : Input

data class GetSessionInfoInput(val id: Long?, val name: String?, val dateFrom: Long?, val dateTo: Long?) : Input

data class DeleteSessionInfoInput(val sessionId: Long) : Input

data class GetSessionEntryInput(val sessionId: Long) : Input
