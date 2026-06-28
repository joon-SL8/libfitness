package com.skjline.fitness.feature.publish.strava.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TokenInfo(
    @SerialName("expires_in") val expiresAt: Int,
    @SerialName("expires_at") val expiresIn: Int,
    @SerialName("token_type") val tokenType: String,
    @SerialName("access_token") val accessToken: String,
    @SerialName("refresh_token") val refreshToken: String? = null,

    // this is only available when validating auth code
    // thus, they are checked for having below scopes
    val scope: String = "activity:write, read",

    @SerialName("id_token") val idToken: String = "",
)