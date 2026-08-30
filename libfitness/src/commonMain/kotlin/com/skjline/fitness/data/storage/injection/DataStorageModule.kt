package com.skjline.fitness.data.storage.injection

import com.skjline.fitness.data.storage.BearerStorage
import com.skjline.fitness.data.storage.StorageDatabase
import com.skjline.fitness.data.storage.usecase.GetTAndCStatusUseCase
import com.skjline.fitness.data.storage.usecase.SetTAndCStatusUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val dataStorageModule = module {
    singleOf<StorageDatabase>(::StorageDatabase)
    singleOf<BearerStorage>(::BearerStorage)

    factoryOf(::GetTAndCStatusUseCase)
    factoryOf(::SetTAndCStatusUseCase)
}
