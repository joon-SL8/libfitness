package com.skjline.fitness.core.security

import com.skjline.fitness.core.security.EncryptionProcessor.Companion.SALT_SIZE
import com.skjline.fitness.core.security.Util.Companion.decodeBase64
import com.skjline.fitness.core.security.Util.Companion.encodeBase64
import com.skjline.fitness.core.utils.Const.Companion.EMPTY
import io.ktor.utils.io.charsets.Charsets
import io.ktor.utils.io.core.toByteArray
import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.CArrayPointer
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.UByteVar
import kotlinx.cinterop.ULongVar
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.alloc
import kotlinx.cinterop.allocArray
import kotlinx.cinterop.get
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import kotlinx.cinterop.usePinned
import kotlinx.cinterop.value
import platform.CoreCrypto.CCCrypt
import platform.CoreCrypto.CC_MD5
import platform.CoreCrypto.CC_MD5_DIGEST_LENGTH
import platform.CoreCrypto.CC_SHA256
import platform.CoreCrypto.CC_SHA256_DIGEST_LENGTH
import platform.CoreCrypto.kCCAlgorithmAES128
import platform.CoreCrypto.kCCBlockSizeAES128
import platform.CoreCrypto.kCCDecrypt
import platform.CoreCrypto.kCCEncrypt
import platform.CoreCrypto.kCCOptionPKCS7Padding
import platform.CoreCrypto.kCCSuccess
import platform.Foundation.NSData
import platform.Foundation.create
import kotlin.random.Random

/**
 * Password encryption handler
 */
@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING", "UNUSED")
@OptIn(ExperimentalForeignApi::class)
public actual class EncryptionHandler actual constructor(
    encryptionSecret: String,
    encryptionSpec: String,
) : EncryptionProcessor {
    actual override val secretPhrase: String = encryptionSecret
    actual override val specPhrase: String = encryptionSpec

    private val secret: NSData = encryptionSecret.toNSData()!!
    private val iv: NSData = encryptionSpec.toNSData()!!

    actual override fun encryptAESCipher(text: String): String =
        aesPerformOperation(
            kCCEncrypt,
            text.toNSData()!!,
        )?.encodeBase64().orEmpty()

    actual override fun decryptAESCipher(encrypted: String): String =
        aesPerformOperation(
            kCCDecrypt,
            encrypted.decodeBase64().toNSData(),
        )?.decodeToString().orEmpty()

    private fun aesPerformOperation(
        op: UInt,
        data: NSData,
    ): ByteArray? =
        memScoped {
            val size: Int = (data.length + kCCBlockSizeAES128).toInt()
            val buffer: CArrayPointer<UByteVar> = allocArray<UByteVar>(size.toLong())

            var readSize: ULongVar = alloc(0UL)
            val result: Int = CCCrypt(
                op,
                kCCAlgorithmAES128,
                kCCOptionPKCS7Padding,
                secret.bytes,
                secret.length,
                iv.bytes,
                data.bytes,
                data.length,
                buffer,
                size.toULong(),
                readSize.ptr,
            )

            when (result) {
                kCCSuccess -> readBuffer(buffer, readSize.value.toInt())

                else -> null
            }
        }

    actual override fun hashMD5(input: String): String {
        val inputData: NSData = input.takeIf { it.isNotEmpty() }?.toNSData() ?: NSData()
        return memScoped {
            val digest: CArrayPointer<UByteVar> = allocArray<UByteVar>(CC_MD5_DIGEST_LENGTH)
            CC_MD5(inputData.bytes, inputData.length.toUInt(), digest)
            readBuffer(digest, CC_MD5_DIGEST_LENGTH).toHexString()
        }
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

    private fun encryptWithSalt(
        text: String,
        salt: String,
    ): String =
        try {
            hashSHA256(text, salt).encodeBase64()
        } catch (_: Exception) {
            EMPTY
        }

    private fun hashSHA256(
        text: String,
        salt: String,
    ): ByteArray {
        val content: ByteArray = text.toByteArray(Charsets.UTF_8)
        val salted: ByteArray = salt.decodeBase64()
        val saltedNSData: NSData = (content + salted).toNSData()
        return memScoped {
            val digest: CArrayPointer<UByteVar> = allocArray<UByteVar>(CC_SHA256_DIGEST_LENGTH)
            CC_SHA256(saltedNSData.bytes, saltedNSData.length.toUInt(), digest)
            readBuffer(digest, CC_SHA256_DIGEST_LENGTH)
        }
    }

    private fun readBuffer(
        source: CArrayPointer<UByteVar>,
        size: Int,
    ): ByteArray {
        val buffer: ByteArray = ByteArray(size)
        for (index in 0 until size) {
            val data: UByte = source[index]
            buffer[index] = data.toByte()
        }
        return buffer
    }

    private fun String?.toNSData(): NSData? = this?.toByteArray(Charsets.UTF_8)?.toNSData()

    @OptIn(BetaInteropApi::class)
    private fun ByteArray.toNSData(): NSData =
        this.usePinned {
            NSData.create(bytes = it.addressOf(0), length = this.size.toULong())
        }

    private fun String.toHexString(): String {
        val hexFormatter: HexFormat =
            HexFormat {
                upperCase = true
                number {
                    removeLeadingZeros = true
                }
            }

        return map { it.code.toHexString(hexFormatter) }.joinToString(EMPTY)
    }
}
