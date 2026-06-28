package com.skjline.fitness.core.model.packet

import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
data class CyclingPowerMeasurementPacket(
    val powerLevel: Int,
    val crankRPM: Int,
    val hrRate: Int = 0,
    val crankUpdateTime: Int = 0,
    val wheelRevolutions: Long? = null,
    val wheelEventTime: Int? = null,
    val timestamp: Long = Clock.System.now().epochSeconds,
) {

    companion object {
        const val PWR_MSR_PDL_BAL_FLAG = 0x01
        const val PWR_MSR_PDL_BAL_REF_FLAG = 0x02
        const val PWR_MSR_ACC_TORQ_FLAG = 0x04
        const val PWR_MSR_ACC_TORQ_SRC_FLAG = 0x08

        const val PWR_MSR_WHEEL_REV = 0x10
        const val PWR_MSR_CRNK_REV = 0x20

        const val PWR_MSR_PDL_BAL_LEN = 1
        const val PWR_MSR_ACC_TORQ_LEN = 2
        const val PWR_MSR_WHEEL_REV_LEN = 6
        const val PWR_MSR_CRNK_REV_LEN = 2
        const val PWR_MSR_PWR_LEN = 2

        fun fromPayload(data: ByteArray = ByteArray(0)): CyclingPowerMeasurementPacket {
            var powerLevel = 0
            var crankRPM = 0
            var crankUpdateTime = 0
            var wheelRevolutions: Long? = null
            var wheelEventTime: Int? = null

            var bytePower: ByteArray? = null
            var byteCrank: ByteArray? = null

            val flag = data.parseOctet(0, 2)
            var offset = 2

            // power always available
            bytePower = byteArrayOf(data[1 + offset], data[offset])
            powerLevel = data.parseOctet(start = offset, octetSize = PWR_MSR_PWR_LEN)
            offset += 2

            if ((flag and PWR_MSR_PDL_BAL_FLAG) != 0) {
                offset += PWR_MSR_PDL_BAL_LEN
            }
            if ((flag and PWR_MSR_ACC_TORQ_FLAG) != 0) {
                offset += PWR_MSR_ACC_TORQ_LEN
            }
            if ((flag and PWR_MSR_WHEEL_REV) != 0) {
                wheelRevolutions = data.parseOctet(start = offset, octetSize = 4).toLong()
                offset += 4
                wheelEventTime = data.parseOctet(start = offset, octetSize = 2)
                offset += 2
            }
            if ((flag and PWR_MSR_CRNK_REV) != 0) {
                crankRPM = data.parseOctet(start = offset, octetSize = PWR_MSR_CRNK_REV_LEN)
                offset += PWR_MSR_CRNK_REV_LEN
                crankUpdateTime = data.parseOctet(start = offset, octetSize = PWR_MSR_CRNK_REV_LEN)
                offset += PWR_MSR_CRNK_REV_LEN
            }

            return CyclingPowerMeasurementPacket(
                powerLevel = powerLevel,
                crankRPM = crankRPM,
                crankUpdateTime = crankUpdateTime,
                wheelRevolutions = wheelRevolutions,
                wheelEventTime = wheelEventTime
            )
        }

        public fun calculateRPM(current: CyclingPowerMeasurementPacket, previous: CyclingPowerMeasurementPacket?): Long {
            if (previous == null || previous.crankUpdateTime == current.crankUpdateTime) return 0L

            val revDelta = current.crankRPM - previous.crankRPM
            var timeDelta = current.crankUpdateTime - previous.crankUpdateTime
            if (timeDelta < 0) {
                timeDelta += 65536
            }
            return ((revDelta * 60 * 1024) / timeDelta).toLong()
        }

        public fun calculateSpeed(current: CyclingPowerMeasurementPacket, previous: CyclingPowerMeasurementPacket?): Double? {
            val currentRev = current.wheelRevolutions ?: return null
            val currentTime = current.wheelEventTime ?: return null
            if (previous == null || previous.wheelEventTime == currentTime) return 0.0

            val revDelta = currentRev - (previous.wheelRevolutions ?: 0L)
            var timeDelta = currentTime - (previous.wheelEventTime ?: 0)
            if (timeDelta < 0) {
                timeDelta += 65536
            }

            val durationSeconds = timeDelta.toDouble() / 2048.0
            if (durationSeconds <= 0) return 0.0

            val wheelCircumference = 2.096 // 700x23C default
            val distanceMeters = revDelta.toDouble() * wheelCircumference
            val speedMps = distanceMeters / durationSeconds

            return speedMps * 2.23694 // mph
        }
    }
}