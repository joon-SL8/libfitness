package com.skjline.fitness.core.security

import android.content.Context
import android.content.SharedPreferences
import com.skjline.fitness.core.model.generic.Const.Companion.EMPTY
import com.skjline.fitness.core.security.EncryptedPropertyManager.Companion.DEFAULT_PREFERENCE
import com.skjline.fitness.core.security.EncryptedPropertyManager.Companion.PREF_KEY_AUTH
import org.koin.core.Koin
import org.koin.mp.KoinPlatform.getKoin

/**
 * Android property manager
 * Android implementation of EncryptedPropertyManager
 *
 * @param koin koin object for locating Android injection
 */
public class AndroidPropertyManager(
    koin: Koin,
) : AbstractEncryptedPropertyManager() {
    private val context: Context = koin.get<Context>()
    private val encryptionProcessor: EncryptionProcessor by lazy { koin.get<EncryptionProcessor>() }

    private val shared: SharedPreferences by lazy {
        context.getSharedPreferences(DEFAULT_PREFERENCE, Context.MODE_PRIVATE)
    }

    override fun storeAuthToken(token: String?) {
        setProperty(PREF_KEY_AUTH, token.orEmpty())
    }

    override fun setProperty(
        key: String,
        value: String,
    ) {
        super.setProperty(key, value)

        val editor: SharedPreferences.Editor = shared.edit()
        if (value.isEmpty()) {
            editor.remove(key)
        } else {
            val encrypted: String = encryptionProcessor.encryptAESCipher(value)
            editor.putString(key, encrypted)
        }.apply()
    }

    override fun getProperty(key: String): String {
        val data: String? = shared.getString(key, EMPTY)
        return if (data != null && data.isNotEmpty()) {
            encryptionProcessor.decryptAESCipher(data)
        } else {
            EMPTY
        }
    }
}

public actual fun getPropertyManager(): EncryptedPropertyManager = AndroidPropertyManager(getKoin())
