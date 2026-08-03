package com.skjline.fitness.util

import com.skjline.fitness.core.model.workout.SessionEntry
import com.skjline.fitness.feature.publish.fit.model.DataRow
import com.skjline.fitness.feature.publish.fit.model.FitContent
import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.allocArrayOf
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.usePinned
import okio.FileSystem
import okio.Path.Companion.toPath
import okio.buffer
import platform.Foundation.NSData
import platform.Foundation.create
import platform.darwin.NSInteger
import platform.posix.memcpy

// for swift for data compatibility converting kotlin <-> swift objects.
@OptIn(ExperimentalForeignApi::class)
interface NSDataUtil {
    companion object {
        fun convertToByteArray(data: NSData?): ByteArray {
            val converted = data?.let { array ->
                val size = array.length.toInt()
                if (size == 0) {
                    return@let null
                }
                ByteArray(size).apply {
                    usePinned {
                        memcpy(it.addressOf(0), array.bytes, array.length)
                    }
                }
            }

            return converted ?: ByteArray(0)
        }
    }
}

const val OFFSET_MS = 631065600000

inline fun FitContent.forEachRecordPerform(block: (NSInteger, DataRow) -> Unit) = records.forEach {
    val recordTS = ((it.key + it.value.id) * 1000L - OFFSET_MS) / 1000L
    val dictValue = it.value

    block(recordTS, dictValue)
}

fun loadFileToByteArray(filename: String): ByteArray {
    println("saving fit on ios: $filename")
    return FileSystem.SYSTEM.source(filename.toPath()).buffer().readByteArray()
}

@OptIn(ExperimentalForeignApi::class)
fun NSData.toByteArray(): ByteArray {
    return ByteArray(length.toInt()).apply {
        usePinned {
            memcpy(it.addressOf(0), bytes, length)
        }
    }
}

@OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)
fun ByteArray.toNSData(): NSData = memScoped {
    NSData.create(
        bytes = allocArrayOf(this@toNSData),
        length = this@toNSData.size.toULong()
    )
}

fun createDataRow(
    id: NSInteger,
    lat: NSInteger,
    long: NSInteger,
    power: NSInteger,
    heart: NSInteger,
    spd: NSInteger,
    cad: NSInteger
): DataRow {
    return DataRow(
        id = id,
        lat = lat,
        long = long,
        power = power.toInt(),
        heartRate = heart.toInt(),
        cadence = cad.toInt(),
        speed = spd.toInt(),
    )
}

fun SessionEntry.toDataRow(
    lat: NSInteger = 0,
    long: NSInteger = 0,
): DataRow {
    return DataRow(id, lat, long, power.toInt(), heart.toInt(), cadence.toInt(), speed.toInt())
}
