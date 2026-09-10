package com.skjline.fitness.core.security

/**
 * Text encryption handler
 */
@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
public expect class EncryptionHandler(
    encryptionSecret: String,
    encryptionSpec: String,
) : EncryptionProcessor {
    override val secretPhrase: String
    override val specPhrase: String

    override fun encryptTextWithRandomSalt(
        text: String,
        salt: String,
    ): Pair<String, String>

    override fun encryptAESCipher(text: String): String

    override fun decryptAESCipher(encrypted: String): String

    override fun hashMD5(input: String): String
}
