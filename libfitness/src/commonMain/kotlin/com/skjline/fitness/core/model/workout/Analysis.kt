package com.skjline.fitness.core.model.workout

import com.skjline.fitness.data.storage.Constants.Companion.PROFILE_KEY_FTP
import com.skjline.fitness.data.storage.DataHandler
import com.skjline.fitness.injection.AppComponent
import org.koin.core.component.inject
import kotlin.math.pow

class Analysis {
    private val storage: DataHandler by AppComponent.inject()

    var ftp: Double = 0.0
        private set

    suspend fun initialize() {
        val profile = storage.getUserProfile(PROFILE_KEY_FTP)
        ftp = try {
            profile?.data_?.toDouble()?.takeIf { it > 0.0 } ?: 1.0
        } catch (e: Exception) {
            println("Error Parsing FTP Profiles: ($profile)")
            1.0
        }
        println("User FTP: $ftp")
    }

    fun averagePower(power: List<Double>): Double {
        return power.average()
    }

    fun normalizedPower(power: List<Double>): Double {
        require(power.size >= 30)

        // 30-second moving average
        val rolling = mutableListOf<Double>()

        for (i in 29 until power.size) {
            val avg = power.subList(i - 29, i + 1).average()
            rolling.add(avg)
        }

        val avgFourth = rolling
            .map { it.pow(4.0) }
            .average()

        return avgFourth.pow(0.25)
    }

    fun calculateIntensityFactor(
        normalizedPower: Double,
    ): Double {
        return normalizedPower / ftp
    }

    fun calculateTrainingStressScore(
        durationSeconds: Long,
        normalizedPower: Double,
        intensityFactor: Double,
    ): Double {
        require(durationSeconds > 0) { "Duration must be greater than zero." }
        return (durationSeconds * normalizedPower * intensityFactor * 100.0) /
                (ftp * 3600.0)
    }

    fun calculateTssForPowerList(
        powers: List<Double>,
        durationSeconds: Long,
    ): Double {
        val np = normalizedPower(powers)
        val tssFactor = (durationSeconds * np * np)
        val tssNormalized = (ftp * ftp * 36)
        val tss = (tssFactor) / (tssNormalized)
        println("Calculating tss($tss) $tssFactor/$tssNormalized for power list($durationSeconds). NP:$np (FTP: $ftp) duration:${durationSeconds/60.0}")
        return tss
    }
}
