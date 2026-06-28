package com.skjline.fitness.data.storage.usecase

import com.skjline.fitness.core.utils.DispatcherProvider
import com.skjline.fitness.data.asset.model.ProfileResult
import com.skjline.fitness.data.storage.StorageDatabase
import com.skjline.fitness.data.storage.input.Input
import com.skjline.fitness.injection.AppComponent
import org.koin.core.component.get

interface DataUseCase<I : Input, R : ProfileResult> {
    suspend operator fun invoke(input: I): R
}

abstract class BaseDataUseCase<I : Input, R : ProfileResult> : DataUseCase<I, R> {
    protected val storage: StorageDatabase = AppComponent.get()
    protected val dispatcherProvider: DispatcherProvider = AppComponent.get()
}
