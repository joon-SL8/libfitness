package com.skjline.fitness.data.storage.usecase

import com.skjline.fitness.core.security.EncryptionProcessor
import com.skjline.fitness.data.asset.model.UpdateAthleteDataResult
import com.skjline.fitness.data.storage.Constants.Companion.PROFILE_KEY_AGE
import com.skjline.fitness.data.storage.Constants.Companion.PROFILE_KEY_FTP
import com.skjline.fitness.data.storage.Constants.Companion.PROFILE_KEY_NAME
import com.skjline.fitness.data.storage.Constants.Companion.PROFILE_KEY_WEIGHT
import com.skjline.fitness.data.storage.input.AthleteProfileDataInput
import com.skjline.fitness.injection.AppComponent
import kotlinx.coroutines.withContext
import org.koin.core.component.inject

class UpdateAthleteProfileUseCase : BaseDataUseCase<AthleteProfileDataInput, UpdateAthleteDataResult>() {
    private val encryptedPropertyManager: EncryptionProcessor by AppComponent.inject<EncryptionProcessor>()

    override suspend operator fun invoke(input: AthleteProfileDataInput): UpdateAthleteDataResult {
        val contents = storage.database.userProfileQueries.getAll().executeAsList()
        val index = contents.size
        withContext(dispatcherProvider.io) {
            insertOrUpdateData(index + 1, encryptedPropertyManager.encryptAESCipher( input.name), PROFILE_KEY_NAME, contents)
            insertOrUpdateData(index + 2, encryptedPropertyManager.encryptAESCipher( "${input.age}"), PROFILE_KEY_AGE, contents)
            insertOrUpdateData(index + 3, encryptedPropertyManager.encryptAESCipher( "${input.ftp}"), PROFILE_KEY_FTP, contents)
            insertOrUpdateData(index + 4, encryptedPropertyManager.encryptAESCipher( "${input.weight}"), PROFILE_KEY_WEIGHT, contents)
        }

        return UpdateAthleteDataResult
    }
}
