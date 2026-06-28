package com.skjline.fitness.feature.publish.strava.usecase

import com.skjline.fitness.feature.publish.strava.Const.Companion.API_BASE
import com.skjline.fitness.feature.publish.strava.api.model.Athlete
import io.ktor.client.call.body
import io.ktor.client.request.get

class GetAthleteInfoUseCase(athleteId: Long) : ApiUseCase() {

    suspend operator fun invoke(): Athlete = try {
        client.client
            .get(ENDPOINT)
            .body()
    } catch (ex: Exception) {
        throw ex
    }

    private companion object {
        const val ENDPOINT = "https://$API_BASE/athlete"
    }
}
