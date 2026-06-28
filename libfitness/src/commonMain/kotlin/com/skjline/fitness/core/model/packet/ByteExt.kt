package com.skjline.fitness.core.model.packet

import kotlin.math.pow

private const val OCTET_DIGIT = 16f * 16f

fun List<Byte>.toInt(): Int {
    return ((this[0].toInt() and 0xff) shl 8) or (this[1].toInt() and 0xff)
}

fun ByteArray.parseOctet(start: Int = 0, octetSize: Int = size): Int {
    if (size <= start + octetSize - 1) return -1

    var sum = 0
    for (offset in (octetSize - 1 downTo 0)) {
        val digit = (this[start + offset].toInt() and 0xff).toDouble() *
                OCTET_DIGIT.pow(offset)
        sum += digit.toInt()
    }
    return sum
}