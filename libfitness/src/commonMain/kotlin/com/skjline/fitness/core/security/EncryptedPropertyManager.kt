package com.skjline.fitness.core.security

/**
 * A Setting Property Manager with encryption on storing values
 * iOS and Android use platform specific secured key-value handlers,
 * keychain items and EncryptedSharedPreferences respectably,
 * providing an encrypted storage for settings.
 */
public interface EncryptedPropertyManager {
    public fun setCallback(callback: (String) -> Unit)

    /**
     * Set Property
     * When persisting a numeric value, it supports Int or Float types.
     * Note: a float value will return as an Int
     *
     * @param key key
     * @param value value
     */
    public fun setProperty(
        key: String,
        value: String,
    )

    public fun getProperty(key: String): String

    public fun isTrue(key: String): Boolean = getProperty(key).lowercase() == "true"

    /**
     * Get Number Preference
     * a convenience method to load the value as a numeric value.
     *
     * a float will be converted to an Int. The values are persisted as a String.
     * For a data precision, directly use [EncryptedPropertyManager.getProperty] method and manually convert as needed.
     *
     * @param key key
     * @param default default 0
     */
    public fun getNumberPreference(
        key: String,
        default: Int = 0,
    ): Int =
        getProperty(key).let {
            if (it.isEmpty()) {
                return default
            }

            return if (it.contains('.')) {
                it.toFloatOrNull()?.toInt()
            } else {
                it.toIntOrNull()
            } ?: default
        }

    public companion object {
        public const val DEFAULT_PREFERENCE: String = "AXSPref"
        public const val PREF_KEY_AUTH: String = "AXSAuthKey"
    }
}

public expect fun getPropertyManager(): EncryptedPropertyManager
