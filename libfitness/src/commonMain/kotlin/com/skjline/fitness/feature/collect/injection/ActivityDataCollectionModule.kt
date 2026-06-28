package com.skjline.fitness.feature.collect.injection

import com.skjline.fitness.feature.collect.service.DataCollectionService
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val activityDataCollectionModule = module {
    singleOf<DataCollectionService>(::DataCollectionService)
}