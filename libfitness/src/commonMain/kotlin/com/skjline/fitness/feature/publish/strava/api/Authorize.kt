package com.skjline.fitness.feature.publish.strava.api

import io.ktor.client.engine.HttpClientEngineFactory

sealed interface AuthorizationCodeResult
data class Succeed(val source: String) : AuthorizationCodeResult
data class Failed(val reason: String) : AuthorizationCodeResult

interface Authorize {
    @Throws(IllegalArgumentException::class)
    fun authenticate(): AuthorizationCodeResult
}

interface ClientFactoryProvider {
    fun createClient(): HttpClientEngineFactory<*>
}

expect fun getStravaAuthorize(deeplink: String): Authorize

expect fun getClientFactoryProvider(): ClientFactoryProvider
