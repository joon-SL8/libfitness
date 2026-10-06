package com.skjline.fitness.data.storage.usecase

import com.skjline.fitness.core.utils.DispatcherProvider
import com.skjline.fitness.data.asset.model.DataResult
import com.skjline.fitness.data.storage.DataHandler
import com.skjline.fitness.data.storage.StorageDatabase
import com.skjline.fitness.data.storage.input.Input
import com.skjline.fitness.injection.AppComponent
import org.koin.core.component.inject

interface DataUseCase<I : Input, R : DataResult> {
    suspend operator fun invoke(input: I): R
}

abstract class BaseDataUseCase<I : Input, R : DataResult> : DataUseCase<I, R> {
    protected val storage: DataHandler by AppComponent.inject()
    protected val database: StorageDatabase by AppComponent.inject()
    protected val dispatcherProvider: DispatcherProvider by AppComponent.inject()

    protected suspend fun insertOrUpdateData(type: String, data: String) {
        println("Exception while trying to insert $type for $data")
        storage.insertOrUpdateUserProfile(type, data)
    }
}
