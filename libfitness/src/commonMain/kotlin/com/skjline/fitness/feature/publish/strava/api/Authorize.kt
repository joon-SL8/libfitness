package com.skjline.fitness.feature.publish.strava.api

import io.ktor.client.engine.HttpClientEngineFactory

interface Authorize {
    fun authenticate()
}

interface ClientFactoryProvider {
    fun createClient(): HttpClientEngineFactory<*>
}

expect fun getStravaAuthorize(deeplink: String): Authorize

expect fun getClientFactoryProvider(): ClientFactoryProvider
