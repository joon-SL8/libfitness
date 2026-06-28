package com.skjline.fitness.feature.publish.fit.usecase

import com.skjline.fitness.core.model.generic.Const.Companion.FIT_FILE_EXT
import com.skjline.fitness.core.model.generic.Const.Companion.TRAINING_DATA_FILENAME_PREAMBLE
import com.skjline.fitness.core.model.generic.PacketType
import com.skjline.fitness.core.model.packet.DataPacket
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
import kotlin.time.Clock.System.now
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
class PublishPackageUseCase(
    private val collection: Map<PacketType, MutableList<DataPacket>>
) {
    private val dispatcherProvider by lazy { AppComponent.get<DispatcherProvider>() }

    var startedOn = now().toEpochMilliseconds()

    private val _staus = MutableStateFlow<PublishState>(Initial)
    val status: StateFlow<PublishState> = _staus.asStateFlow()

    operator fun invoke() {
        packCollection()
    }

    private fun packCollection() {
        CoroutineScope(dispatcherProvider.io + Job()).launch {
            val fit by lazy { AppComponent.get<FitProcessor>() }
            val filename = "$TRAINING_DATA_FILENAME_PREAMBLE-$startedOn.$FIT_FILE_EXT"

            val content = FitContent(records = transformCollectedDataToRecords())
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

    private fun transformCollectedDataToRecords(): Map<Long, DataRow> {
        val record = mutableMapOf<Long, DataRow>()

        collection.forEach { entry ->
            when (entry.key) {
                PacketType.HRData, PacketType.Power, PacketType.Cadence, PacketType.Speed -> {
                    entry.value.groupBy { it.timestamp }.forEach { group ->
                        val timestamp = group.key
                        group.value.firstOrNull()?.let {
                            val data = addToRecord(
                                entry.key, record[timestamp] ?: DataRow(), it.data.getValueAsNumber()
                            )
                            record[timestamp] = data
                        }
                    }
                }

                else -> {
                    // do nothing
                }
            }
        }

        return record
    }

    private fun addToRecord(type: PacketType, recordDataRow: DataRow, value: Int): DataRow =
        when (type) {
            PacketType.Power -> recordDataRow.copy(power = value)
            PacketType.HRData -> recordDataRow.copy(heartRate = value)
            PacketType.Cadence -> recordDataRow.copy(cadence = value)
            PacketType.Speed -> recordDataRow.copy(speed = value)
            else -> recordDataRow
        }


    fun onUploadCompleted() {
        (DataUploadComplete(OnUploadSucceed))
    }
}
