package com.skjline.fitness.core.security

import com.skjline.fitness.core.utils.Const.Companion.EMPTY
import org.koin.core.Koin
import kotlin.io.encoding.Base64

/**
 * Utils
 *
 * Utility methods and/or parameters for library initialization
 */
internal class Util {
    public companion object {
        /**
         * Is Valid
         *
         * Validate if the given string is formatted UUID
         *
         * @param uuid String UUID
         * @return true when it's formatted properly
         */
        public fun String.isValidUUID(uuid: String): Boolean {
            val content: String = uuid.trim()
            if (content.isEmpty()) {
                return false
            }

            val format: Regex =
                "^[0-9a-f]{8}(?:-[0-9a-f]{4}){3}-[0-9a-f]{12}$".toRegex(RegexOption.IGNORE_CASE)
            return content.matches(format)
        }

        public fun ByteArray.encodeBase64(): String {
            if (isEmpty()) {
                return EMPTY
            }

            return Base64.encode(this)
        }

        public fun String.decodeBase64(): ByteArray {
            if (isEmpty()) {
                return ByteArray(0)
            }

            val padding: Int = length % 4
            val padded: String = takeIf { padding == 0 } ?: (this + "=".repeat(4 - padding))

            val bytes: ByteArray = try {
                Base64.decode(padded)
            } catch (_: Exception) {
                ByteArray(0)
            }

            return bytes
        }
    }
}

/**
 * Run DB scripts
 *
 * Allows to run DB alter query.
 * e.g. val query = "DROP TABLE IF EXISTS Event;"
 *
 * @param koin Koin instance
 */
public suspend fun runCustomizedDBScripts(koin: Koin) {
    val queries: List<String> = listOf()
//    val data: DataProvider = koin.get<DatabaseStorage>()
//    queries.forEach { query ->
//        data.executeSupplementaryQuery(query)
//    }
}
