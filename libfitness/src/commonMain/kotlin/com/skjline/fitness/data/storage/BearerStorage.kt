package com.skjline.fitness.data.storage

import com.skjline.fitness.core.utils.DispatcherProvider
import com.skjline.fitness.injection.AppComponent
import io.ktor.client.plugins.auth.providers.BearerTokens
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.get
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

// need to implement a method to persist the token and/or create token with additional security
@OptIn(ExperimentalTime::class)
class BearerStorage {

    private val dispatcherProvider = AppComponent.get<DispatcherProvider>()
    private val db = AppComponent.get<StorageDatabase>()

    // need a method to persist token for later usage, ie when reopen the app
    private val onContentChanged: MutableStateFlow<StorageContentChanged> =
        MutableStateFlow(StorageContentChanged.Initialized)
    val onStorageContentChanged = onContentChanged.asStateFlow()

    private val bearer = mutableListOf<BearerTokens>()

    fun addToken(token: BearerTokens) {
        bearer.add(token)

        CoroutineScope(dispatcherProvider.io).launch {
            println("add token: ${token.accessToken}(${token.refreshToken})")
            updateDatabase(token)
            onContentChanged.emit(StorageContentChanged.StravaClientToken(token))
        }
    }

    fun getToken(): BearerTokens? {
        val token = bearer.lastOrNull()
        if (token != null || token?.accessToken?.isNotEmpty() == true) {
            return token
        }

        val resultant = db.database.userEntityQueries.getAll().executeAsList()
        if (resultant.isEmpty()) {
            return null
        }
        resultant.last().let {
            println("restore token from database ${it.bearer}")
            bearer.add(BearerTokens(it.bearer, it.refresh))
        }
        return bearer.lastOrNull()
    }

    private fun updateDatabase(token: BearerTokens) {
        token.refreshToken?.let { refreshToken ->
            db.database.userEntityQueries.insert(
                id = bearer.size + 1L,
                service = "strava",
                bearer = token.accessToken,
                refresh = refreshToken,
                lastAccessed = Clock.System.now().toEpochMilliseconds().toString(),
            )
        }
    }

    private companion object {
        const val KEY_ID = 1L
    }
}