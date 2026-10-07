package com.skjline.fitness.data.storage

interface DataHandler {
    suspend fun getUserProfile(profile: String): UserProfileEntity?
    suspend fun insertOrUpdateUserProfile(profileName: String, content: String): UserProfileEntity?
}
