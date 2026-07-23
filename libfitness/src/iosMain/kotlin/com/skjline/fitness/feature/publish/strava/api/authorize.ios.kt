package com.skjline.fitness.feature.publish.strava.api

import com.skjline.fitness.core.utils.DispatcherProvider
import com.skjline.fitness.feature.publish.strava.usecase.AuthorizationCodeUseCase
import com.skjline.fitness.injection.AppComponent
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.darwin.Darwin
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.get
import platform.AuthenticationServices.ASPresentationAnchor
import platform.AuthenticationServices.ASWebAuthenticationPresentationContextProvidingProtocol
import platform.AuthenticationServices.ASWebAuthenticationSession
import platform.Foundation.NSURL
import platform.UIKit.UIApplication
import platform.darwin.NSObject

actual fun getClientFactoryProvider(): ClientFactoryProvider {
    return object : ClientFactoryProvider {
        override fun createClient(): HttpClientEngineFactory<*> {
            return Darwin
        }
    }
}

class ContextProvider : NSObject(), ASWebAuthenticationPresentationContextProvidingProtocol {
    override fun presentationAnchorForWebAuthenticationSession(session: ASWebAuthenticationSession): ASPresentationAnchor {
        return UIApplication.sharedApplication.keyWindow
    }
}

private suspend fun processAppLinkContent(link: String?) {
    val lnk = link ?: ""
    println("processing applink content $link")
    val codeUseCase = AuthorizationCodeUseCase(lnk)

    val result = codeUseCase.invoke()
    if (result !is AuthorizationCodeUseCase.AuthorizationCodeResult.Succeed) {
        // show error modal?
        println("error processing authorization code $result")
    } else {
        println("authorization code processed $result")
    }
}

actual fun getStravaAuthorize(deeplink: String): Authorize {
    println("get authorize class $deeplink")

    val dispatcherProvider = AppComponent.get<DispatcherProvider>()
    val oauthStravaScheme =
        NSURL(string = "strava://oauth/mobile/authorize?client_id=132336&redirect_uri=https%3A%2F%2Fskjline&response_type=code&approval_prompt=auto&scope=activity:write,read")
    val oauthWebScheme =
        NSURL(string = "https://www.strava.com/oauth/mobile/authorize?client_id=132336&response_type=code&approval_prompt=auto&scope=activity%3Awrite%2Cread&redirect_uri=Skjline%3A%2F%2Fskjline")

    return object : Authorize {
        override fun authenticate() {
            println("initiate authenticate")
            val canOpen = UIApplication.sharedApplication.canOpenURL(oauthStravaScheme)
            println("Is Strava installed? $canOpen")
            if (canOpen) {
                // user has Strava App installed
                UIApplication.sharedApplication.openURL(oauthStravaScheme)
            } else {
                val session = ASWebAuthenticationSession(
                    uRL = oauthWebScheme,
                    callbackURLScheme = deeplink,
                ) { url, error ->
                    println("perform authorization $url")

                    val context = dispatcherProvider.io + Job()
                    CoroutineScope(context).launch {
                        withContext(context) {
                            processAppLinkContent(url?.toString())
                        }
                    }
                }

                session.presentationContextProvider = ContextProvider()
                session.start()
            }
        }
    }
}
