package com.skjline.fitness.core.security

import com.skjline.fitness.core.model.generic.Const.Companion.EMPTY

/**
 * Text encryption handler
 */
public interface EncryptionProcessor {
    public val secretPhrase: String
    public val specPhrase: String

    public fun encryptTextWithRandomSalt(
        text: String,
        salt: String = EMPTY,
    ): Pair<String, String>

    public fun encryptAESCipher(text: String): String

    public fun decryptAESCipher(encrypted: String): String

    public fun hashMD5(input: String): String

    /**
     * Encryption Constants
     */
    public companion object {
        public const val SALT_SIZE: Int = 32
    }
}
