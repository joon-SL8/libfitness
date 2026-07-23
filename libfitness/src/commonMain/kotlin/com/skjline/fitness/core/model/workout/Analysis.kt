package com.skjline.fitness.core.model.workout

import com.skjline.fitness.data.storage.Constants.Companion.PROFILE_KEY_FTP
import com.skjline.fitness.data.storage.StorageDatabase
import com.skjline.fitness.injection.AppComponent
import org.koin.core.component.get
import kotlin.math.pow

class Analysis {
    private val storage = AppComponent.get<StorageDatabase>()
    var ftp: Double = 0.0
        private set

    suspend fun initialize() {
        val profiles = storage.database.userProfileQueries.getAll().executeAsList()
        val profilesDebug = profiles.joinToString { "${it.name}: ${it.data_}" }
        ftp = (profiles.firstOrNull { it.name == PROFILE_KEY_FTP }?.data_?.toDouble()?.takeIf { it > 0.0 } ?: 1.0)
        println("FTP: $ftp - Profiles: $profilesDebug")
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
