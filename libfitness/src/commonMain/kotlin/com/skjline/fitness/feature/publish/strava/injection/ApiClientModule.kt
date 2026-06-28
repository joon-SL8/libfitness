package com.skjline.fitness.feature.publish.strava.injection

import com.skjline.fitness.feature.publish.strava.api.Client
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val apiClientModule = module {
    singleOf<Client>(::Client)
}