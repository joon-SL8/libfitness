package com.skjline.fitness.feature.publish.strava.usecase

import com.skjline.fitness.injection.AppComponent
import com.skjline.fitness.data.storage.BearerStorage
import com.skjline.fitness.feature.publish.strava.Const.Companion.AUTH_TOKEN_URL
import com.skjline.fitness.feature.publish.strava.Const.Companion.CLIENT_ID
import com.skjline.fitness.feature.publish.strava.Const.Companion.CLIENT_SECRET
import com.skjline.fitness.feature.publish.strava.Const.Companion.KEY_PARAM_REQUEST_CLIENT_ID
import com.skjline.fitness.feature.publish.strava.Const.Companion.KEY_PARAM_REQUEST_CLIENT_SECRET
import com.skjline.fitness.feature.publish.strava.Const.Companion.KEY_PARAM_REQUEST_CODE
import com.skjline.fitness.feature.publish.strava.Const.Companion.KEY_PARAM_REQUEST_GRANT_TYPE
import com.skjline.fitness.feature.publish.strava.Const.Companion.KEY_PARAM_REQUEST_GRANT_TYPE_AUTH_VALUE
import com.skjline.fitness.feature.publish.strava.api.model.TokenInfo
import io.ktor.client.call.body
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.request.forms.submitForm
import io.ktor.http.parameters
import org.koin.core.component.get

class AuthorizationResolveCodeUseCase : ApiUseCase() {
    private val bearerStorage: BearerStorage = AppComponent.get<BearerStorage>()

    suspend operator fun invoke(code: String): Boolean {
        val token = try {
            println("submit code for JWT")
            // create a request and resolve the code to a Strava auth token
            client.client.submitForm(
                url = AUTH_TOKEN_URL,
                formParameters = parameters {
                    append(KEY_PARAM_REQUEST_GRANT_TYPE, KEY_PARAM_REQUEST_GRANT_TYPE_AUTH_VALUE)
                    append(KEY_PARAM_REQUEST_CODE, code)
                    append(KEY_PARAM_REQUEST_CLIENT_ID, CLIENT_ID)
                    append(KEY_PARAM_REQUEST_CLIENT_SECRET, CLIENT_SECRET)
                }
            ).body<TokenInfo>()
        } catch (ex: Exception) {
            ex.printStackTrace()
            return false
        }

        println("persisting JWT for network activity")
        // set token to bearer storage for usage
        bearerStorage.addToken(
            BearerTokens(token.accessToken, token.refreshToken.orEmpty())
        )
        return true
    }
}