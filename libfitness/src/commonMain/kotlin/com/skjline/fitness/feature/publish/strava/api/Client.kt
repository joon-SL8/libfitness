package com.skjline.fitness.feature.publish.strava.api

import com.skjline.fitness.core.utils.DispatcherProvider
import com.skjline.fitness.data.storage.BearerStorage
import com.skjline.fitness.data.storage.StorageContentChanged
import com.skjline.fitness.feature.publish.strava.usecase.refreshAuthorizationTokenUseCase
import com.skjline.fitness.injection.AppComponent
import io.ktor.client.HttpClient
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.authProviders
import io.ktor.client.plugins.auth.providers.BearerAuthProvider
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import org.koin.core.component.get
import org.koin.core.component.inject

class Client {
    private val bearerStorage: BearerStorage by AppComponent.inject()
    private val dispatcherProvider: DispatcherProvider by AppComponent.inject()

    private val tokenRefreshListener by lazy {
        CoroutineScope(context = dispatcherProvider.io + Job())
    }

    private val clientFactoryProvider = getClientFactoryProvider()

    val client = HttpClient(clientFactoryProvider.createClient()) {
//        engine {
//            maxConnectionsCount = 1000
//            endpoint {
//                maxConnectionsPerRoute = 100
//                pipelineMaxSize = 20
//                keepAliveTime = 5000
//                connectTimeout = 5000
//                connectAttempts = 5
//            }
//        }

        install(Auth) {
            bearer {
                loadTokens {
                    bearerStorage.getToken()
                }

                refreshTokens {
                    refreshAuthorizationTokenUseCase()
                    bearerStorage.getToken()
                }
            }
        }

        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                ignoreUnknownKeys = true
                coerceInputValues = true
            })
        }
    }

    init {
        tokenRefreshListener.launch {
            bearerStorage.onStorageContentChanged.collectLatest { state ->
                if (state !is StorageContentChanged.StravaClientToken) {
                    return@collectLatest
                }

                val token = state.token
                if (token.accessToken.isBlank()) {
                    return@collectLatest
                }

                // clear existing token to trigger refresh
                client.authProviders.filterIsInstance<BearerAuthProvider>()
                    .firstOrNull()?.clearToken()
            }
        }
    }

    fun onClose() {
        client.close()
        tokenRefreshListener.cancel()
    }
}