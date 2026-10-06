package com.skjline.fitness.data.storage.usecase

import com.skjline.fitness.data.asset.model.GetDataResult
import com.skjline.fitness.data.storage.DataHandler
import com.skjline.fitness.data.storage.input.Input
import com.skjline.fitness.injection.AppComponent
import org.koin.core.component.inject

abstract class BaseGetDataUseCase<I : Input> : DataUseCase<I, GetDataResult> {
    protected val storage: DataHandler by AppComponent.inject()

    protected suspend fun getData(type: String): GetDataResult {
        val content = storage.getUserProfile(type)?.let { profile ->
            profile.data_
        }
        return GetDataResult(data = content.orEmpty())
    }
}
