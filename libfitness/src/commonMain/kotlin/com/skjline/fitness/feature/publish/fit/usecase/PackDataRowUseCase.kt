package com.skjline.fitness.feature.publish.fit.usecase

import com.skjline.fitness.core.model.generic.Const.Companion.FIT_FILE_EXT
import com.skjline.fitness.core.model.generic.Const.Companion.TRAINING_DATA_FILENAME_PREAMBLE
import com.skjline.fitness.core.model.workout.SessionEntry
import com.skjline.fitness.core.utils.DispatcherProvider
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
import org.koin.core.component.get
import org.koin.core.component.inject

class PackDataRowUseCase {
    private val dispatcherProvider: DispatcherProvider by AppComponent.inject()

    private val _staus = MutableStateFlow<PublishState>(Initial)
    val status: StateFlow<PublishState> = _staus.asStateFlow()

    operator fun invoke(timestampStart: Long, rows: List<SessionEntry>) {
        packCollection(timestampStart, rows)
    }

    private fun packCollection(timestampStart: Long, rows: List<SessionEntry>) {
        CoroutineScope(dispatcherProvider.io + Job()).launch {
            val fit by lazy { AppComponent.get<FitProcessor>() }
            val filename = "$TRAINING_DATA_FILENAME_PREAMBLE-$timestampStart.$FIT_FILE_EXT"

            val content = FitContent(records = transformCollectedDataToRecords(timestampStart, rows))
            println("create fit file with records $filename")
            val result = fit.processFitFileData(filename, content)

            val uploadState = if (result !is FileCreateResult.Success) {
                DataUploadComplete(OnUploadFailed)
            } else {
                DataUploadReady(OnUploadReady(filename = result.fileUrl))
            }

            _staus.emit(uploadState)
        }
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
        return DataRow(lat, long, power.toInt(), heart.toInt(), cadence.toInt(), speed.toInt())
    }

    fun onUploadCompleted() {
        (DataUploadComplete(OnUploadSucceed))
    }
}
