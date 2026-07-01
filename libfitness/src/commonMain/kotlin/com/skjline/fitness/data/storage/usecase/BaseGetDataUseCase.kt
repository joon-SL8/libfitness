package com.skjline.fitness.data.storage.usecase

import com.skjline.fitness.core.utils.DispatcherProvider
import com.skjline.fitness.data.asset.model.GetDataResult
import com.skjline.fitness.data.storage.StorageDatabase
import com.skjline.fitness.data.storage.input.Input
import com.skjline.fitness.injection.AppComponent
import org.koin.core.component.inject

abstract class BaseGetDataUseCase<I : Input> : DataUseCase<I, GetDataResult> {
    protected val storage: StorageDatabase by AppComponent.inject()
    protected val dispatcherProvider: DispatcherProvider by AppComponent.inject()

    protected suspend fun getData(type: String): GetDataResult {
        val contents = storage.database.userProfileQueries.getAll().executeAsList()
        return GetDataResult(data = contents.firstOrNull { it.name == type }?.data_.orEmpty())
    }
}
