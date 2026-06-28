package com.skjline.fitness.core.model.workout

abstract class Workout {

    protected fun getEntry(line: String): Pair<String, Any?> {
        val data = line.split("=")
        return if (data.size >= 2) {
            Pair(data[0], data[1])
        } else {
            Pair(line, null)
        }
    }

    companion object {
        const val BUFFER_SIZE = 4096

        const val BEGIN_HEADER = "COURSE HEADER"
        const val END_HEADER = "END COURSE HEADER"
        const val BEGIN_DATA = "COURSE DATA"
        const val END_DATA = "END COURSE DATA"
        const val BEGIN_CUE = "COURSE TEXT"
        const val END_CUE = "END COURSE TEXT"

        const val VERSION = "VERSION"
        const val UNITS = "UNITS"
        const val DESCRIPTION = "DESCRIPTION"
        const val FILENAME = "FILE NAME"
        const val FTP = "FTP"
        const val MIN_PERC = "MINUTES PERCENT"
        const val MIN_WATTS = "MINUTES WATTS"
    }
}