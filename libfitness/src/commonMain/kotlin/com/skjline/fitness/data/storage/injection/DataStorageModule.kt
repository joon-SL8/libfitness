package com.skjline.fitness.data.storage.injection

import com.skjline.fitness.data.storage.BearerStorage
import com.skjline.fitness.data.storage.StorageDatabase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val dataStorageModule = module {
    singleOf<StorageDatabase>(::StorageDatabase)
    singleOf<BearerStorage>(::BearerStorage)
}
