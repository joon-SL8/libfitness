package com.skjline.fitness.data.storage

import com.skjline.fitness.core.security.EncryptionProcessor
import com.skjline.fitness.injection.AppComponent
import org.koin.core.component.inject
import kotlin.time.Clock.System.now

class ProfileDataHandler(
    private val database: FitnessDatabase,
) : DataHandler {
    private val encryptedPropertyManager: EncryptionProcessor by AppComponent.inject()

    private suspend fun getNextIndex(): Long {
        return database.userProfileQueries.getCount().executeAsOne() + 1L
    }

    override suspend fun getUserProfile(profile: String): UserProfileEntity? {
        val encrypted = database.userProfileQueries
            .getForKey(profile).executeAsOneOrNull()

        return encrypted?.let { data ->
            UserProfileEntity(
                id = data.id,
                name = data.name,
                data_ = encryptedPropertyManager.decryptAESCipher(data.data_),
                updated = data.updated,
            )
        }
    }

    override suspend fun insertOrUpdateUserProfile(profileName: String, content: String): UserProfileEntity? {
        val encrypted = try {
            encryptedPropertyManager.encryptAESCipher(content)
        } catch (e: Exception) {
            println("encryption failed: ${e.message}")
            return null
        }

        getUserProfile(profileName)?.let { profile ->
            println("trying to insert $profile")
            database.userProfileQueries.updateData(
                data = encrypted,
                id = profile.id,
            )
        } ?: database.userProfileQueries.insert(
            id = getNextIndex(),
            name = profileName,
            data_ = encrypted,
            updated = now().toEpochMilliseconds(),
        )

        return getUserProfile(profileName)
    }

}