package com.skjline.fitness.data.storage.usecase

import com.skjline.fitness.core.utils.DispatcherProvider
import com.skjline.fitness.data.asset.model.DataResult
import com.skjline.fitness.data.storage.StorageDatabase
import com.skjline.fitness.data.storage.UserProfileEntity
import com.skjline.fitness.data.storage.input.Input
import com.skjline.fitness.injection.AppComponent
import org.koin.core.component.inject
import kotlin.time.Clock.System.now

interface DataUseCase<I : Input, R : DataResult> {
    suspend operator fun invoke(input: I): R
}

abstract class BaseDataUseCase<I : Input, R : DataResult> : DataUseCase<I, R> {
    protected val storage: StorageDatabase by AppComponent.inject()
    protected val dispatcherProvider: DispatcherProvider by AppComponent.inject()

    protected suspend fun insertOrUpdateData(index: Int, data: String, type: String, contents: List<UserProfileEntity>) {
        contents.findLast {
            it.name == type
        }?.let { content ->
            storage.database.userProfileQueries
                .updateData(id = content.id, data = data)
        } ?: run {
            storage.database.userProfileQueries.insert(
                id = index.toLong(),
                name = type,
                data_ = data,
                updated = now().epochSeconds,
            )
        }
    }
}
