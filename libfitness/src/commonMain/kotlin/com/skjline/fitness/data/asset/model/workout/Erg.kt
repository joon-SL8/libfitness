package com.skjline.fitness.data.asset.model.workout

import com.skjline.fitness.core.model.workout.Workout
import kotlinx.serialization.Serializable

@Serializable
data class ErgHeader(
    val filename: String = "",
    val units: String = "",
    val version: Int = 0,
    val ftp: Int = 0,
    val description: String = "",
    val courses: List<Course> = emptyList(),
    val cues: List<Cue> = emptyList(),
) : Workout() {

    fun createHeader(lines: List<String>): ErgHeader {
        val queue = ArrayDeque(lines)
        var header = ErgHeader()

        do {
            val l = queue.removeFirst()

            val (key, value) = getEntry(l)
            header = when (key) {
                FTP -> header.copy(ftp = value.toString().toInt())
                VERSION -> header.copy(version = value.toString().toInt())
                DESCRIPTION -> header.copy(description = value.toString())
                FILENAME -> header.copy(filename = value.toString())
                UNITS -> header.copy(units = value.toString())
                BEGIN_DATA -> header.copy(courses = parseData(queue))
                BEGIN_CUE -> header.copy(cues = parseCue(queue))
                else -> header
            }
        } while (l != END_HEADER)
        return header
    }

    fun parseData(queue: ArrayDeque<String>): List<Course> =
        queue.takeWhile {
            it != END_DATA
        }.map {
            val (key, content) = getEntry(it)
            Course(key.toFloat(), content.toString().toInt())
        }

    fun parseCue(queue: ArrayDeque<String>): List<Cue> =
        queue.takeWhile {
            it != END_DATA
        }.map {
            val (key, content) = getEntry(it)
            Cue(key.toFloat(), content.toString(), 0)
        }
}
