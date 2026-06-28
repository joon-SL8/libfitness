package com.skjline.fitness.feature.publish.strava.usecase

import com.skjline.fitness.core.model.generic.Const.Companion.EMPTY
import com.skjline.fitness.core.utils.DispatcherProvider
import com.skjline.fitness.injection.AppComponent
import kotlinx.coroutines.withContext
import org.koin.core.component.get

class AuthorizationCodeUseCase(
    private val link: String = EMPTY,
    private val dispatcherProvider: DispatcherProvider = AppComponent.get(),
) {
    suspend operator fun invoke(): AuthorizationCodeResult = withContext(dispatcherProvider.io) {
        if (link.isEmpty()) {
            return@withContext AuthorizationCodeResult.Failed("AppLink: empty content")
        }

        val scode = link.indexOf(APPLINK_PARAM_CODE) + APPLINK_PARAM_CODE.length
        val ecode = link.indexOf(APPLINK_PARAM_SEPARATOR_CHAR, scode)
        val code = if (scode > 0) {
            val end = if (ecode > 0) ecode else link.length - scode
            link.substring(scode, end)
        } else {
            return@withContext AuthorizationCodeResult.Failed("AppLink: code not found ($scode, $ecode)")
        }

        val sscopes = link.indexOf(APPLINK_PARAM_SCOPE) + APPLINK_PARAM_SCOPE.length
        val scopes = if (sscopes > 0) {
            link.substring(sscopes)
                .replace(APPLINK_PARAM_COLON_UNICODE, APPLINK_PARAM_SEPARATOR_COLON)
                .split(APPLINK_PARAM_SLASH_UNICODE)
        } else {
            return@withContext AuthorizationCodeResult.Failed("AppLink: scope not found")
        }

        return@withContext loadBearerFromToken(code)
    }

    private suspend fun loadBearerFromToken(code: String): AuthorizationCodeResult {
        AuthorizationCodeResult.Failed("Processing authorization code: $code")
        val consumeAuthCode = AuthorizationResolveCodeUseCase(code)
        return if (consumeAuthCode()) {
            AuthorizationCodeResult.Succeed("AppLink")
        } else {
            AuthorizationCodeResult.Failed("AppLink: Unable to convert")
        }
    }

    sealed class AuthorizationCodeResult {
        data class Succeed(val source: String) : AuthorizationCodeResult()
        data class Failed(val reason: String) : AuthorizationCodeResult()
    }

    private companion object {
        const val APPLINK_PARAM_CODE = "code="
        const val APPLINK_PARAM_SCOPE = "scope="

        const val APPLINK_PARAM_SEPARATOR_CHAR = '&'
        const val APPLINK_PARAM_SEPARATOR_COLON = ":"
        const val APPLINK_PARAM_COLON_UNICODE = "%3A"
        const val APPLINK_PARAM_SLASH_UNICODE = "%2C"
    }
}