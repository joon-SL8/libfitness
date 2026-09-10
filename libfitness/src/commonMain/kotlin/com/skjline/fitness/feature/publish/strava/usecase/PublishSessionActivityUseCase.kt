package com.skjline.fitness.feature.publish.strava.usecase

import com.skjline.fitness.core.Platform
import com.skjline.fitness.core.model.generic.Const.Companion.EMPTY
import com.skjline.fitness.data.storage.input.UpdateSessionPublishInput
import com.skjline.fitness.data.storage.usecase.UpdateSessionPublishedOnUseCase
import com.skjline.fitness.feature.publish.fit.encoder.FitProcessor
import com.skjline.fitness.feature.publish.strava.Const.Companion.API_BASE
import com.skjline.fitness.feature.publish.strava.Const.Companion.ISO_8601_FORMAT
import com.skjline.fitness.feature.publish.strava.Const.Companion.APP_HOME_URL
import com.skjline.fitness.injection.AppComponent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.client.statement.bodyAsText
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.Parameters
import io.ktor.http.parameters
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern
import kotlinx.datetime.toLocalDateTime
import org.koin.core.component.get
import kotlin.time.Instant

class PublishSessionActivityUseCase : ApiUseCase() {
    private val processState = MutableStateFlow<PublishResult>(PublishResult.Init)
    val status = processState.asStateFlow()

    private val platform = AppComponent.get<Platform>()

    private fun String.userDescription() = this.ifEmpty {
        "$DESCRIPTION_HEADER from ${platform.name}\n\n$APP_HOME_URL"
    }

    @OptIn(FormatStringsInDatetimeFormats::class)
    private val formatter = LocalDateTime.Format {
        byUnicodePattern(ISO_8601_FORMAT)
    }

    suspend operator fun invoke(
        title: String = EMPTY,
        sessionId: Long = 0L,
        startTime: Long = 0L,
        duration: Long = 0L,
        distance: Long = 0L,
        description: String = EMPTY,
        fitFilename: String,
    ) {
        println("start processing the fit file ($fitFilename)")
        val localDateTime = Instant.fromEpochMilliseconds(startTime)
            .toLocalDateTime(TimeZone.currentSystemDefault())
        val formatted = localDateTime.format(formatter)

        val params: Parameters = parameters {
            append(KEY_PARAM_NAME, title.ifEmpty { NAME_HEADER })
            append(KEY_PARAM_TYPE, TYPE_HEADER)
            append(KEY_PARAM_SPORT_TYPE, SPORT_HEADER)
            append(KEY_PARAM_START_DATE, formatted)
            append(KEY_PARAM_ELAPSED_TIME, (duration / 1000).toString())
            append(KEY_PARAM_DESCRIPTION, description.userDescription())
            append(KEY_PARAM_DISTANCE, distance.toString())
            append(KEY_PARAM_TRAINER, 1.toString())
            append(KEY_PARAM_COMMUTE, 0.toString())
        }

        val fitProcessor = AppComponent.get<FitProcessor>()
        val bytes = fitProcessor.loadFitFile(fitFilename)
        val result = try {
            println("uploading activity: ${bytes.size}")
            val response = client.client.submitFormWithBinaryData(
                url = API_ENDPOINT,
                formData = formData {
                    append(KEY_PARAM_FILE_TYPE, bytes, Headers.build {
                        append(HttpHeaders.ContentType, KEY_PARAM_VALUE_CONTENT_APP_TYPE)
                        append(
                            HttpHeaders.ContentDisposition,
                            "$KEY_PARAM_FILENAME_TYPE=$fitFilename"
                        )
                    })
                    append(KEY_PARAM_NAME, title.ifEmpty { NAME_HEADER })
                    append(KEY_PARAM_DATA_TYPE, FILE_TYPE_VALUE)
                    append(KEY_PARAM_DESCRIPTION, description.userDescription())
                    append(KEY_PARAM_TRAINER, 1.toString())
                    append(KEY_PARAM_COMMUTE, 0.toString())
                }
            ).bodyAsText()
            println(response)

            val input = UpdateSessionPublishInput(sessionId, sessionId, "")
            UpdateSessionPublishedOnUseCase().invoke(input)

            PublishResult.Success(response)
        } catch (ex: Exception) {
            println(ex.message)
            PublishResult.Failed("${ex.message}")
        }
        processState.emit(result)
    }

    sealed class PublishResult {
        data object Init : PublishResult()
        data object InProgress : PublishResult()

        // returns success with file location
        data class Success(val response: String) : PublishResult()
        data class Failed(val error: String) : PublishResult()
    }

    private companion object {
        const val NAME_HEADER = "Skjline Cycling"
        const val DESCRIPTION_HEADER = "Skjline mobile training session"
        const val TYPE_HEADER = "Ride"
        const val SPORT_HEADER = "VirtualRide"

        // uploads API will create an activity with a fit file
        const val API_ENDPOINT = "https://${API_BASE}uploads"

        const val KEY_PARAM_NAME = "name"
        const val KEY_PARAM_TYPE = "type"
        const val KEY_PARAM_SPORT_TYPE = "sport_type"
        const val KEY_PARAM_START_DATE = "start_date_local"
        const val KEY_PARAM_ELAPSED_TIME = "elapsed_time"
        const val KEY_PARAM_DESCRIPTION = "description"
        const val KEY_PARAM_DISTANCE = "distance"
        const val KEY_PARAM_TRAINER = "trainer"
        const val KEY_PARAM_COMMUTE = "commute"

        const val KEY_PARAM_VALUE_CONTENT_APP_TYPE = "application/*"

        const val KEY_PARAM_FILE_TYPE = "file"
        const val KEY_PARAM_FILENAME_TYPE = "filename"
        const val KEY_PARAM_DATA_TYPE = "data_type"
        const val FILE_TYPE_VALUE = "fit"
    }
}
