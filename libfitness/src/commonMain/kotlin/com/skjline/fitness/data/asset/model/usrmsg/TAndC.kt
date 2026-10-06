package com.skjline.fitness.data.asset.model.usrmsg

public data class TAndCConfiguration(
    val appName: String,
    val effectiveDate: String,
    val lastUpdated: String,
    val legalContactEmail: String,
    val websiteUrl: String,
    val privacyContactEmail: String
)

public fun String.replaceTAndCPlaceholders(config: TAndCConfiguration): String {
    return this.replace("[App Name]", config.appName)
        .replace("[Effective Date]", config.effectiveDate)
        .replace("[Last Updated]", config.lastUpdated)
        .replace("[Legal Contact Email]", config.legalContactEmail)
        .replace("[Website URL]", config.websiteUrl)
        .replace("[Privacy Contact Email]", config.privacyContactEmail)
}

public enum class TAndC {
    TermsOfUse,
    SafetyDisclaimer,
    PrivacyPolicy,
    SessionWarning,
    ;

    fun getDescription(): String {
        return when (this) {
            TermsOfUse -> com.skjline.fitness.data.asset.model.usrmsg.TermsOfUse.TERMS_OF_USE
            SafetyDisclaimer -> com.skjline.fitness.data.asset.model.usrmsg.SafetyDisclaimer.SAFETY_DISCLAIMER
            PrivacyPolicy -> com.skjline.fitness.data.asset.model.usrmsg.PrivacyPolicy.PRIVACY_POLICY
            SessionWarning -> com.skjline.fitness.data.asset.model.usrmsg.SessionBeginWarning.SESSION_BEGIN_WARNING
        }
    }

    companion object {
        const val KEY_EFFECTIVE_DATE = "Effective Date"
        const val KEY_LAST_UPDATED = "Last Updated"
        const val KEY_APP_NAME = "App Name"
        const val KEY_LEGAL_CONTACT = "Legal Contact Email"
        const val KEY_APP_WEBSITE = "Website URL"

        public const val TERMS_OF_USE: String = com.skjline.fitness.data.asset.model.usrmsg.TermsOfUse.TERMS_OF_USE
        public const val SAFETY_DISCLAIMER: String = com.skjline.fitness.data.asset.model.usrmsg.SafetyDisclaimer.SAFETY_DISCLAIMER
        public const val PRIVACY_POLICY: String = com.skjline.fitness.data.asset.model.usrmsg.PrivacyPolicy.PRIVACY_POLICY
        public const val SESSION_BEGIN_WARNING: String = com.skjline.fitness.data.asset.model.usrmsg.SessionBeginWarning.SESSION_BEGIN_WARNING
    }
}
