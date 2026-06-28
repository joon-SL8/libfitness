package com.skjline.fitness.data.asset.useCase

import com.skjline.fitness.core.model.workout.MrcCourse

class AssetFileParseUseCase(
    private val file: String,
    private val lines: List<String>,
) {
    operator fun invoke(): MrcCourse? {
        var start = false

        var mrc = MrcCourse(shortFileName = file)
        val courses = mutableListOf<Pair<Float, Float>>()

        lines.forEach { line ->
            if (line.contains("[END COURSE DATA]")) {
                return@forEach
            } else if (line.contains("[COURSE DATA]")) {
                start = true
            } else if (line.isBlank() || !start) {
                if (line.isBlank().not()) {
                    mrc = when {
                        "VERSION" in line -> mrc.copy(
                            version = line.replace(" ", "").split("=").last()
                        )

                        "UNITS" in line -> mrc.copy(
                            units = line.replace(" ", "").split("=").last()
                        )

                        "DESCRIPTION" in line -> mrc.copy(
                            description = line.replace(" ", "").split("=").last()
                        )

                        "FILE NAME" in line -> mrc.copy(
                            filename = line.replace(" ", "").split("=").last()
                        )

                        else -> mrc
                    }
                }
            } else {
                val data = line.split("\t")

                val time = data[0].toFloat()
                val percent = data[1].toInt().toFloat()
                courses.add(Pair(time, percent))
            }
        }

        if (courses.isNotEmpty()) {
            mrc = mrc.copy(course = courses)
        }

        return mrc
    }
}
