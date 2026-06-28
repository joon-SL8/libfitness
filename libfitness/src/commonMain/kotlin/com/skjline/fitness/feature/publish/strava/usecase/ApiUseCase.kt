package com.skjline.fitness.feature.publish.strava.usecase

import com.skjline.fitness.injection.AppComponent
import com.skjline.fitness.feature.publish.strava.api.Client
import org.koin.core.component.get

abstract class ApiUseCase(
    protected val client: Client = AppComponent.get<Client>(),
)