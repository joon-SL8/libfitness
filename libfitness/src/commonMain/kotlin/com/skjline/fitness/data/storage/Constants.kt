package com.skjline.fitness.data.storage

interface Constants {
    companion object {
        const val USER_PROFILE_KEY_FIRST_TIME = "first_time"

        const val PROFILE_KEY_PRE = "key"
        const val KEY_CREDENTIAL = "$PROFILE_KEY_PRE::credential"

        const val PROFILE_KEY_FTP = "$PROFILE_KEY_PRE::ftp"
        const val PROFILE_KEY_WEIGHT = "$PROFILE_KEY_PRE::weight"
        const val PROFILE_KEY_MAX_HR = "$PROFILE_KEY_PRE::heart_rate"

        const val PROFILE_KEY_NAME = "$PROFILE_KEY_PRE::name"
        const val PROFILE_KEY_AGE = "$PROFILE_KEY_PRE::age"

    }
}