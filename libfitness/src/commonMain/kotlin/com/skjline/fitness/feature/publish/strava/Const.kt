package com.skjline.fitness.feature.publish.strava

class Const {
    companion object {
        const val APP_HOME_URL = "https://localhost"

        // move to config
        const val CLIENT_NAME = "skjline"
        const val CLIENT_ID = "132336"
        const val CLIENT_SECRET = "e45619c36226bc013de5c99bade9dabc15819225"

        const val ISO_8601_FORMAT = "uuuu-MM-dd'T'HH:mm:ss'Z'"
        const val SERVER = "www.strava.com"
        const val API_BASE = "$SERVER/api/v3/"

        const val AUTH_TOKEN_URL = "https://$API_BASE/oauth/token"
        const val AUTH_AUTHORIZE = "https://www.strava.com/oauth/mobile/authorize"

        const val KEY_PARAM_REQUEST_CODE = "code"
        const val KEY_PARAM_REQUEST_CLIENT_ID = "client_id"
        const val KEY_PARAM_REQUEST_GRANT_TYPE = "grant_type"
        const val KEY_PARAM_REQUEST_CLIENT_SECRET = "client_secret"

        const val KEY_PARAM_REQUEST_GRANT_TYPE_AUTH_VALUE = "authorization_code"
        const val KEY_PARAM_REQUEST_GRANT_TYPE_REFRESH_VALUE = "refresh_token"
    }
}
