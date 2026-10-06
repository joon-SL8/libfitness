package com.skjline.fitness.core.security

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull

class EncryptionHandlerTest {
    private val handler = EncryptionHandler("1234567890abcdef", "12345678")

    @Test
    fun testEncryption() {
        val originalText = "Hello, World!"
        val expected = "oQVEJ8Uwbau8SkQN7X2tjw=="

        val actual = handler.encryptAESCipher(originalText)

        assertNotNull(actual)
        assertEquals(expected, actual)
    }

    @Test
    fun testDecryption() {
        val originalText = "oQVEJ8Uwbau8SkQN7X2tjw=="
        val expected = "Hello, World!"

        val actual = handler.decryptAESCipher(originalText)

        assertEquals(expected, actual)
    }

    @Test
    fun testHashMD5() {
        val input = "TestInput"
        val hash = handler.hashMD5(input)
        assertNotNull(hash)
        assertNotEquals(input, hash)
    }
}
