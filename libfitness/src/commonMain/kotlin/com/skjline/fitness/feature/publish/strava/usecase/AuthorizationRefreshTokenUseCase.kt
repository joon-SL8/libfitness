package com.skjline.fitness.feature.publish.strava.usecase

import com.skjline.fitness.data.storage.BearerStorage
import com.skjline.fitness.feature.publish.strava.Const.Companion.AUTH_TOKEN_URL
import com.skjline.fitness.feature.publish.strava.Const.Companion.CLIENT_ID
import com.skjline.fitness.feature.publish.strava.Const.Companion.CLIENT_SECRET
import com.skjline.fitness.feature.publish.strava.Const.Companion.KEY_PARAM_REQUEST_CLIENT_ID
import com.skjline.fitness.feature.publish.strava.Const.Companion.KEY_PARAM_REQUEST_CLIENT_SECRET
import com.skjline.fitness.feature.publish.strava.Const.Companion.KEY_PARAM_REQUEST_GRANT_TYPE
import com.skjline.fitness.feature.publish.strava.Const.Companion.KEY_PARAM_REQUEST_GRANT_TYPE_REFRESH_VALUE
import com.skjline.fitness.feature.publish.strava.api.Client
import com.skjline.fitness.feature.publish.strava.api.model.TokenInfo
import com.skjline.fitness.injection.AppComponent
import io.ktor.client.plugins.auth.AuthCircuitBreaker
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.request.forms.submitForm
import io.ktor.client.statement.bodyAsText
import io.ktor.http.parameters
import kotlinx.serialization.json.Json
import org.koin.core.component.get

// this use case is intended for a direct consumption by a client
// should be invoked on its builder's refreshToken block
suspend fun Client.refreshAuthorizationTokenUseCase(
    bearerStorage: BearerStorage = AppComponent.get()
): Boolean {
    val old = bearerStorage.getToken()

    val response = client.submitForm(
        url = AUTH_TOKEN_URL,
        formParameters = parameters {
            append(
                KEY_PARAM_REQUEST_GRANT_TYPE,
                KEY_PARAM_REQUEST_GRANT_TYPE_REFRESH_VALUE
            )
            append(
                KEY_PARAM_REQUEST_CLIENT_ID,
                CLIENT_ID
            )
            append(
                KEY_PARAM_REQUEST_CLIENT_SECRET,
                CLIENT_SECRET
            )
            append(
                KEY_PARAM_REQUEST_GRANT_TYPE_REFRESH_VALUE,
                old?.refreshToken.orEmpty()
            )
        }
    ) {
        attributes.put(AuthCircuitBreaker, Unit)
    }.bodyAsText()

    val token = try {
        Json.decodeFromString<TokenInfo>(response)
    } catch (ex: Exception) {
        println("unable to parse as TokenInfo: $response")
        ex.printStackTrace()
        return false
    }

    bearerStorage.addToken(
        BearerTokens(token.accessToken, old?.refreshToken!!)
    )
    return true
}