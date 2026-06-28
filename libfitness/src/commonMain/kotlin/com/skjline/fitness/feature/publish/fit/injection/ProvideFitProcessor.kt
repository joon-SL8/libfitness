package com.skjline.fitness.feature.publish.fit.injection

import com.skjline.fitness.feature.publish.fit.encoder.FitProcessor
import org.koin.core.module.Module
import org.koin.dsl.module

fun provideFitProcessor(processor: FitProcessor): Module = module {
    single<FitProcessor> { processor }
}