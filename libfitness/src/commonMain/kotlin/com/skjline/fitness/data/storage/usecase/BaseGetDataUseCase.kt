package com.skjline.fitness.data.storage.usecase

import com.skjline.fitness.core.security.EncryptionProcessor
import com.skjline.fitness.core.utils.Const.Companion.EMPTY
import com.skjline.fitness.core.utils.DispatcherProvider
import com.skjline.fitness.data.asset.model.GetDataResult
import com.skjline.fitness.data.storage.StorageDatabase
import com.skjline.fitness.data.storage.input.Input
import com.skjline.fitness.injection.AppComponent
import org.koin.core.component.inject

abstract class BaseGetDataUseCase<I : Input> : DataUseCase<I, GetDataResult> {
    protected val storage: StorageDatabase by AppComponent.inject()
    private val encryptedPropertyManager: EncryptionProcessor by AppComponent.inject<EncryptionProcessor>()

    protected suspend fun getData(type: String): GetDataResult {
        val contents = storage.database.userProfileQueries.getAll().executeAsList()
        val decrypted = contents.firstOrNull { it.name == type }?.let { entity ->
            entity.data_.takeIf { data -> data.isNotEmpty() }
        }?.let { encrypted ->
            encryptedPropertyManager.decryptAESCipher(encrypted)
        } ?: EMPTY

        return GetDataResult(data = decrypted)
    }
}
