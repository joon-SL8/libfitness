package com.skjline.fitness.core.model.packet

public class PowerControlCommands {
    public fun getStartCommand(): ByteArray {
            return byteArrayOf(FMCP_START)
    }

    public fun getStopCommand(): ByteArray {
        return byteArrayOf(FMCP_STOP_PAUSE, FMCP_VALUE_STOP)
    }

    public fun getPauseCommand(): ByteArray {
        return byteArrayOf(FMCP_STOP_PAUSE, FMCP_VALUE_PAUSE)
    }

    public fun getSetTargetPowerCommand(watts: Int): ByteArray {
        return byteArrayOf(
            FMCP_SET_TARGET_POWER,
            (watts and 0xFF).toByte(),
            ((watts shr 8) and 0xFF).toByte()
        )
    }

    private companion object Companion {
        // Fitness Machine Control Procedure Commands & Values
        const val FMCP_REQUEST_CONTROL: Byte = 0x00
        const val FMCP_SET_TARGET_POWER: Byte = 0x05
        const val FMCP_START: Byte = 0x07
        const val FMCP_STOP_PAUSE: Byte = 0x08

        const val FMCP_VALUE_STOP: Byte = 0x01
        const val FMCP_VALUE_PAUSE: Byte = 0x02
    }
}
