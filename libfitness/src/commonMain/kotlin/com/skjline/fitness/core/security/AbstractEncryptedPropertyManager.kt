package com.skjline.fitness.core.security

import com.skjline.fitness.core.security.EncryptedPropertyManager.Companion.PREF_KEY_AUTH

/**
 * Abstract encrypted property manager
 *
 * Handles non-platform specific operations
 */
public abstract class AbstractEncryptedPropertyManager : EncryptedPropertyManager {
    private var onDataChanging: (String) -> Unit = {}

    internal abstract fun storeAuthToken(token: String?)

    internal open fun restoreAuthToken(): String = getProperty(PREF_KEY_AUTH)

    override fun setCallback(callback: (String) -> Unit) {
        onDataChanging = callback
    }

    override fun setProperty(
        key: String,
        value: String,
    ) {
        when (key) {
            PREF_KEY_AUTH -> {
            }

            else -> {
                val last: String = getProperty(key)
                if (last != value) {
                    onDataChanging(key)
                }
            }
        }
    }
}
