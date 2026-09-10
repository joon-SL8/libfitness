package com.skjline.fitness.core.security

import com.skjline.fitness.core.security.EncryptionProcessor.Companion.SALT_SIZE
import com.skjline.fitness.core.security.Util.Companion.decodeBase64
import com.skjline.fitness.core.security.Util.Companion.encodeBase64
import io.ktor.utils.io.charsets.Charsets
import io.ktor.utils.io.core.toByteArray
import java.security.MessageDigest
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec
import kotlin.random.Random

/**
 * Text encryption handler
 */
@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
public actual class EncryptionHandler actual constructor(
    encryptionSecret: String,
    encryptionSpec: String,
) : EncryptionProcessor {
    actual override val secretPhrase: String = encryptionSecret
    actual override val specPhrase: String = encryptionSpec

    private val secret: SecretKey =
        if (secretPhrase.isEmpty()) {
            KeyGenerator.getInstance(AES_ALGORITHM)
                .apply { init(AES_KEY_SIZE) }.generateKey()
        } else {
            SecretKeySpec(secretPhrase.toByteArray(), AES_ALGORITHM)
        }

    private val iv: IvParameterSpec

    init {
        iv =
            if (specPhrase.isEmpty()) {
                val bytes: ByteArray = ByteArray(IV_SIZE)
                SecureRandom().nextBytes(bytes)
                IvParameterSpec(bytes)
            } else {
                IvParameterSpec(specPhrase.toByteArray())
            }
    }

    @OptIn(ExperimentalStdlibApi::class)
    actual override fun encryptAESCipher(text: String): String {
        val cipher: Cipher = Cipher.getInstance(CIPHER_TRANSFORMATION)
        cipher.init(Cipher.ENCRYPT_MODE, secret, iv)

        val hashed: ByteArray = cipher.doFinal(text.toByteArray())
        return hashed.encodeBase64()
    }

    @OptIn(ExperimentalStdlibApi::class)
    actual override fun decryptAESCipher(encrypted: String): String {
        val text: ByteArray = encrypted.decodeBase64()
        val cipher: Cipher = Cipher.getInstance(CIPHER_TRANSFORMATION)

        cipher.init(Cipher.DECRYPT_MODE, secret, iv)

        val hashed: ByteArray = cipher.doFinal(text)
        return String(cipher.doFinal(text))
    }

    @OptIn(ExperimentalStdlibApi::class)
    actual override fun hashMD5(input: String): String =
        MessageDigest.getInstance(MD5_ALGORITHM)
            .digest(input.toByteArray(Charsets.UTF_8))
            .toHexString()

    @OptIn(ExperimentalStdlibApi::class)
    private fun encryptWithSalt(
        text: String,
        salt: String,
    ): String {
        val pwd: ByteArray = text.toByteArray(Charsets.UTF_8)
        val salted: ByteArray = salt.decodeBase64()

        val digest: ByteArray =
            MessageDigest.getInstance(SHA256)
                .digest(pwd + salted)
        return digest.encodeBase64()
    }

    actual override fun encryptTextWithRandomSalt(
        text: String,
        salt: String,
    ): Pair<String, String> =
        salt.ifEmpty {
            Random.nextBytes(SALT_SIZE).encodeBase64()
        }.let { normSalt: String ->
            Pair(encryptWithSalt(text, normSalt), normSalt)
        }

    @OptIn(ExperimentalStdlibApi::class)
    private fun String.toHexString(): String {
        val hexFormatter: HexFormat =
            HexFormat {
                upperCase = true
                number {
                    removeLeadingZeros = true
                }
            }

        return map { it.code.toHexString(hexFormatter) }.joinToString("")
    }

    private companion object {
        const val IV_SIZE = 16
        const val AES_KEY_SIZE = 256

        const val AES_ALGORITHM = "AES"
        const val MD5_ALGORITHM = "MD5"
        const val SHA256 = "SHA-256"
        const val CIPHER_TRANSFORMATION = "AES/CBC/PKCS5Padding"
    }
}
