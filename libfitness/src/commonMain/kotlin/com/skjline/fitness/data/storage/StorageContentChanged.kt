package com.skjline.fitness.data.storage

import io.ktor.client.plugins.auth.providers.BearerTokens

sealed class StorageContentChanged {
    data object Initialized : StorageContentChanged()
    data class StravaClientToken(val token: BearerTokens) : StorageContentChanged()
}