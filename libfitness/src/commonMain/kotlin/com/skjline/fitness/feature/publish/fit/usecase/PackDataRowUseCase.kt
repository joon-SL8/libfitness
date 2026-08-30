package com.skjline.fitness.feature.publish.fit.usecase

import com.skjline.fitness.core.model.generic.Const.Companion.FIT_FILE_EXT
import com.skjline.fitness.core.model.generic.Const.Companion.TRAINING_DATA_FILENAME_PREAMBLE
import com.skjline.fitness.core.model.workout.SessionEntry
import com.skjline.fitness.core.utils.DispatcherProvider
import com.skjline.fitness.data.storage.input.UpdateSessionPublishInput
import com.skjline.fitness.data.storage.usecase.UpdateSessionPublishedOnUseCase
import com.skjline.fitness.feature.publish.fit.encoder.FileCreateResult
import com.skjline.fitness.feature.publish.fit.encoder.FitProcessor
import com.skjline.fitness.feature.publish.fit.model.DataRow
import com.skjline.fitness.feature.publish.fit.model.DataUploadComplete
import com.skjline.fitness.feature.publish.fit.model.DataUploadReady
import com.skjline.fitness.feature.publish.fit.model.FitContent
import com.skjline.fitness.feature.publish.fit.model.Initial
import com.skjline.fitness.feature.publish.fit.model.OnUploadFailed
import com.skjline.fitness.feature.publish.fit.model.OnUploadReady
import com.skjline.fitness.feature.publish.fit.model.OnUploadSucceed
import com.skjline.fitness.feature.publish.fit.model.PublishState
import com.skjline.fitness.injection.AppComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.get
import org.koin.core.component.inject

class PackDataRowUseCase {
    private val dispatcherProvider: DispatcherProvider by AppComponent.inject()

    private val _staus = MutableStateFlow<PublishState>(Initial)
    val status: StateFlow<PublishState> = _staus.asStateFlow()

    suspend operator fun invoke(sessionId: Long, timestampStart: Long, rows: List<SessionEntry>): PublishState {
        return packCollection(sessionId, timestampStart, rows)
    }

    private suspend fun packCollection(sessionId: Long, timestampStart: Long, rows: List<SessionEntry>):
            PublishState = withContext(dispatcherProvider.io + Job()) {
        val fit by lazy { AppComponent.get<FitProcessor>() }
        val filename = "$TRAINING_DATA_FILENAME_PREAMBLE-$timestampStart.$FIT_FILE_EXT"

        val content = FitContent(records = transformCollectedDataToRecords(timestampStart, rows))
        val result = fit.processFitFileData(filename, content)

        val uploadState = if (result !is FileCreateResult.Success) {
            DataUploadComplete(OnUploadFailed)
        } else {
            CoroutineScope(coroutineContext + Job()).launch {
                println("uploading fit file with records $filename")
                val input = UpdateSessionPublishInput(sessionId, 0, result.fileUrl)
                UpdateSessionPublishedOnUseCase().invoke(input)
            }

            DataUploadReady(OnUploadReady(filename = result.fileUrl))
        }

        _staus.emit(uploadState)
        return@withContext uploadState
    }

    private fun transformCollectedDataToRecords(timestampStart: Long, rows: List<SessionEntry>): Map<Long, DataRow> {
        val record: Map<Long, DataRow> = rows.withIndex().associateBy(
            { timestampStart + (it.index * 1_000L) },
            { it.value.toDataRow(0, 0) }
        )

        return record
    }

    private fun SessionEntry.toDataRow(
        lat: Long = 0,
        long: Long = 0,
    ): DataRow {
        return DataRow(id, lat, long, power.toInt(), heart.toInt(), cadence.toInt(), speed.toInt())
    }

    fun onUploadCompleted() {
        (DataUploadComplete(OnUploadSucceed))
    }
}
