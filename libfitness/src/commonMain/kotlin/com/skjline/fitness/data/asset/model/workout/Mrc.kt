package com.skjline.fitness.data.asset.model.workout

import com.skjline.fitness.core.model.workout.Workout
import kotlinx.serialization.Serializable

@Serializable
data class MrcHeader(
    val filename: String = "",
    val units: String = "",
    val version: Int = 0,
    val description: String = "",
    val courses: List<Course> = emptyList(),
) : Workout() {

    fun createHeader(lines: List<String>): MrcHeader {
        val queue = ArrayDeque(lines)
        var header = MrcHeader()

        do {
            val l = queue.removeFirst()

            val (key, value) = getEntry(l)
            header = when (key) {
                VERSION -> header.copy(version = value.toString().toInt())
                DESCRIPTION -> header.copy(description = value.toString())
                FILENAME -> header.copy(filename = value.toString())
                UNITS -> header.copy(units = value.toString())
                BEGIN_DATA -> header.copy(courses = parseData(queue))
                else -> header
            }
        } while (l != END_HEADER)
        return header
    }

    fun parseData(queue: ArrayDeque<String>): List<Course> {
        val data: List<Course> = queue.takeWhile {
            it != END_DATA
        }.map {
            val (key, content) = getEntry(it)
            Course(key.toFloat(), content.toString().toInt())
        }

        return data
    }

    fun parseCue(queue: ArrayDeque<String>): List<Cue> =
        queue.takeWhile {
            it != END_DATA
        }.map {
            val (key, content) = getEntry(it)
            Cue(key.toFloat(), content.toString(), 0)
        }
}
