@file:OptIn(ExperimentalTime::class)

package com.skjline.fitness.core.utils

import com.skjline.fitness.core.utils.Const.Companion.EMPTY
import com.skjline.fitness.core.utils.Const.Companion.INT_DISABLED
import com.skjline.fitness.core.utils.Const.Companion.INT_ENABLED
import com.skjline.fitness.core.utils.Const.Companion.SPACE
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.number
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.json.Json
import org.koin.core.qualifier.named
import org.koin.mp.KoinPlatform.getKoin
import kotlin.enums.enumEntries
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

/**
 * Const
 * Wraps constants used throughout the library
 */
public interface Const {
    public companion object {
        public const val DEFAULT_ID: Long = -1L

        public const val EMPTY: String = ""
        public const val SPACE: String = " "
        public const val LIST_DELIMITER: String = ","
        public const val LIST_SEMICOLON_DELIMITER: String = ";"

        public const val PARALLEL_COUNT: Int = 4

        public const val INT_ENABLED: Int = 1
        public const val INT_DISABLED: Int = 0
        public const val BEFORE_RANGE: Int = -1
        public const val AFTER_RANGE: Int = 1
        public const val IN_RANGE: Int = 0
        public const val INVALID_RANGE: Int = -9

        public const val KEY_OPERATOR_ID: String = "operatorID"

        public const val KEY_CONTEXT_ID: String = "contextID"
        public const val KEY_EVENT_IDS: String = "eventIDs"
        public const val KEY_VENUE_ID: String = "venueID"
        public const val KEY_CLUSTER_CODE: String = "clusterCodeID"
        public const val KEY_GATE_ID: String = "gateID"
        public const val KEY_LOCATION_ID: String = "locationID"
        public const val KEY_PROFILE_ID: String = "profileID"
        public const val KEY_SCAN_MODE: String = "scanModeID"
        public const val KEY_EVENT_TYPE: String = "eventType"
        public const val KEY_OFFLINE_QUEUE_SIZE: String = "offlineQueueSize"

        public const val KEY_SETTINGS_PERIOD: String = "settings_update_period"
        public const val KEY_FLASH_ENABLED: String = "flash_enabled"

        public const val KEY_USER_ID: String = "userID"

        public const val KEY_CACHE_ONLY_MODE: String = "enabled_cache_mode"
        public const val KEY_PAGING_ENABLED: String = "enable_page_mode"

        public const val KEY_SCAN_SKIP_FILTERS: String = "scan_skip_filters"

        public const val PRE_TEMPLATE: String = "{\"TraceId\":\"\",\"ResponseStatus\":"
        public const val POST_TEMPLATE: String = "}"

        public const val RESPONSE_GENERIC_ERROR_CODE: String = "ResponseContainsError"
        public const val RESPONSE_PARSE_ERROR_CODE: String = "ResponseParsingError"
        public const val RESPONSE_ERROR: String = "Error occurred when parsing"
    }
}

public fun Boolean?.toInt(): Int = this?.let { if (this) INT_ENABLED else null } ?: INT_DISABLED

public fun Boolean?.toLong(): Long = this.toInt().toLong()

public fun <T> T?.default(default: T): T = this ?: default

public fun Int?.orDefault(default: Int = 0): Int = default(default)

public fun Int?.toBoolean(): Boolean = this?.let { it > 0 } ?: false

public fun Long?.orDefault(default: Long = 0L): Long = default(default)

public fun Long?.toIntWithDefault(default: Int = 0): Int = orDefault(default.toLong()).toInt()

public fun Long?.toBoolean(): Boolean = this?.let { it > 0 } ?: false

public fun Int?.toSafeLong(): Long = default(0).toLong()

public fun String?.toSafeLong(): Long = takeUnless { it.isNullOrBlank() }?.toLongOrNull() ?: 0L

public fun String?.toPropertyValue(default: Int = 0): Int = toNullableInt() ?: default

private fun String?.toNullableInt(): Int? =
    this?.let {
        if (it.isNotBlank()) {
            try {
                it.toInt()
            } catch (_: Exception) {
                null
            }
        } else {
            null
        }
    }

// convert utc date/time string to localized local date/time
public fun String?.toLocalDateTime(timeZone: TimeZone): LocalDateTime {
    if (!isNullOrBlank()) {
        val trimmed: String = trim().replace("'", "")
        // timezone string for AC2 customization
        if (!trimmed.startsWith("0001")) {
            try {
                return Instant.parse(trimmed).toLocalDateTime(timeZone)
            } catch (_: Exception) {
            }
        }
    }

    return getNowInstant().toEpochMilliseconds().toLocalDateTime(timeZone)
}

// convert utc date/time string to utc local date/time
public fun String.toUTCTime(): LocalDateTime = toLocalDateTime(TimeZone.UTC)

// epoch millisecond time formatter
public fun String.toEpochMilliseconds(): Long =
    try {
        Instant.parse(this).toEpochMilliseconds()
    } catch (_: Exception) {
        0L
    }

public fun String?.toIdList(): List<Long> =
    if (!isNullOrBlank()) {
        split(",").map { it.trim().toLong() }
    } else {
        emptyList()
    }

public fun Instant.toStringTime(): String = toLocalDateTime(TimeZone.UTC).toFormattedTime()

public fun Int.toPadString(pad: Int = 2): String = toString().padStart(pad, '0')

public fun LocalDateTime.toFormattedTime(): String =
    "${year.toPadString(4)}-${month.number.toPadString()}-" +
        "${date.day.toPadString()}T${hour.toPadString()}:${minute.toPadString()}:${second.toPadString()}." +
        "${nanosecond.toString().padStart(7, '0').substring(0..6)}Z"

public fun getNow(timeZone: TimeZone = TimeZone.UTC): LocalDateTime = getNowInstant().toLocalDateTime(timeZone)

public fun getNowInstant(): Instant = Clock.System.now()

public fun initTime(): LocalDateTime = Instant.fromEpochMilliseconds(0).toLocalDateTime(TimeZone.UTC)

public fun LocalDateTime.toStartOfTheDay(): LocalDateTime =
    "${year.toPadString(4)}-${month.number.toPadString()}-${date.day.toPadString()}T00:00:00.0000000Z"
        .toLocalDateTime(TimeZone.UTC)

public fun Long.toLocalDateTime(timeZone: TimeZone = TimeZone.UTC): LocalDateTime =
    Instant.fromEpochMilliseconds(this).toLocalDateTime(timeZone)

public fun LocalDateTime.mins(min: Int): LocalDateTime =
    let {
        val ts: Long = it.toInstant(TimeZone.UTC).toEpochMilliseconds() + (min * 60 * 1000)
        Instant.fromEpochMilliseconds(ts)
    }.toLocalDateTime(TimeZone.UTC)

public fun LocalDateTime.hours(hour: Int): LocalDateTime = mins(hour * 60)

public fun LocalDateTime.days(day: Int): LocalDateTime = hours(day * 24)

public inline fun <reified E : Enum<E>> String.toEnum(): E? = enumEntries<E>().firstOrNull { it.name.equals(this, ignoreCase = true) }

public inline fun <reified E : Enum<E>> String.toEnumWithDefault(default: E): E = toEnum() ?: default

public fun String.removeSpace(): String = this.replace(SPACE, EMPTY)

public object LocalJson {
    public val json: Json =
        Json {
            ignoreUnknownKeys = true
            encodeDefaults = false
        }
}

public inline fun <reified T : Any> inject(): Lazy<T> = lazy { getKoin().get<T>() }

public inline fun <reified T : Any> inject(qualifier: String): Lazy<T> = lazy { getKoin().get<T>(qualifier = named(qualifier)) }

public inline fun <reified T> T.toJson(): String = LocalJson.json.encodeToString<T>(value = this)

public inline fun <reified T> String.fromJson(): T = LocalJson.json.decodeFromString<T>(string = this)
