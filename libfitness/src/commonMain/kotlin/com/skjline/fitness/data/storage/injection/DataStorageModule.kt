package com.skjline.fitness.data.storage.injection

import com.skjline.fitness.data.storage.BearerStorage
import com.skjline.fitness.data.storage.DataHandler
import com.skjline.fitness.data.storage.ProfileDataHandler
import com.skjline.fitness.data.storage.StorageDatabase
import com.skjline.fitness.data.storage.usecase.GetTAndCStatusUseCase
import com.skjline.fitness.data.storage.usecase.SetTAndCStatusUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val dataStorageModule = module {
    singleOf<BearerStorage>(::BearerStorage)
    singleOf<StorageDatabase>(::StorageDatabase)

    factoryOf(::GetTAndCStatusUseCase)
    factoryOf(::SetTAndCStatusUseCase)

    factory<DataHandler> { ProfileDataHandler(get<StorageDatabase>().database) }
}
