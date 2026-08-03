package com.skjline.fitness.feature.publish.strava.api

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.net.Uri
import com.skjline.fitness.feature.publish.strava.Const.Companion.AUTH_AUTHORIZE
import com.skjline.fitness.feature.publish.strava.Const.Companion.CLIENT_ID
import com.skjline.fitness.feature.publish.strava.Const.Companion.CLIENT_NAME
import com.skjline.fitness.injection.AppComponent
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.core.component.get

actual fun getStravaAuthorize(deeplink: String): Authorize {
    return object : Authorize {
        override fun authenticate() : AuthorizationCodeResult {
            val context: Context = AppComponent.get<Context>()

            val intentUri = Uri.parse(AUTH_AUTHORIZE)
                .buildUpon()
                .appendQueryParameter("client_id", CLIENT_ID)
                .appendQueryParameter("redirect_uri", "https://$CLIENT_NAME")
                .appendQueryParameter("response_type", "code")
                .appendQueryParameter("approval_prompt", "auto")
                .appendQueryParameter("scope", "activity:write,read")
                .build()

            context.startActivity(Intent(Intent.ACTION_VIEW, intentUri).apply {
                addFlags(FLAG_ACTIVITY_NEW_TASK)
            })
            return Succeed("")
        }
    }
}

actual fun getClientFactoryProvider(): ClientFactoryProvider {
    return object : ClientFactoryProvider {
        override fun createClient(): HttpClientEngineFactory<*> {
            return OkHttp
        }
    }
}
