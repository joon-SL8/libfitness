package com.skjline.fitness.data.storage.usecase

import com.skjline.fitness.core.model.workout.Session
import com.skjline.fitness.data.asset.model.GetSessionResult
import com.skjline.fitness.data.asset.model.UpdateSessionResult
import com.skjline.fitness.data.storage.input.DeleteSessionInfoInput
import com.skjline.fitness.data.storage.input.GetSessionInfoInput
import com.skjline.fitness.data.storage.input.InsertSessionInfoInput
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
class UpdateSessionUseCase : BaseDataUseCase<InsertSessionInfoInput, UpdateSessionResult>() {
    override suspend operator fun invoke(input: InsertSessionInfoInput): UpdateSessionResult {
        val session = input.data.takeIf { input.data.id == 0L }?.let {
            val id = storage.database.activitySessionQueries.getMaxId().executeAsOneOrNull() ?: 0L
            input.data.copy(id = id + 1)
        } ?: input.data
        with(session) {
            storage.database.activitySessionQueries.insert(
                id = id,
                name = name,
                description = description,
                sessionDate = sessionDate,
                duration = duration,
                mrcFilename = mrcFilename,
                mrcFilepath = mrcFilepath,
                sessionFilename = sessionFilename,
            )
        }

        return UpdateSessionResult(session.id)
    }
}

class GetSessionUseCase : BaseDataUseCase<GetSessionInfoInput, GetSessionResult>() {
    override suspend operator fun invoke(input: GetSessionInfoInput): GetSessionResult {
        val contents = with(input) {
            name?.let {
                storage.database.activitySessionQueries.getByName(it).executeAsList()
            } ?: dateFrom?.let {
                val to = dateTo ?: (it + (7 * 24 * 60 * 60 * 1000))
                storage.database.activitySessionQueries.getByDateRange(it, to).executeAsList()
            } ?: throw IllegalArgumentException("Name or Date is required")
        }.map {
            Session(
                id = it.id,
                name = it.name,
                description = it.description.orEmpty(),
                sessionDate = it.sessionDate,
                duration = it.duration,
                mrcFilename = it.mrcFilename,
                mrcFilepath = it.mrcFilepath,
                sessionFilename = it.sessionFilename,
            )
        }

        return GetSessionResult(contents)
    }
}

class DeleteSessionInfoUseCase : BaseDataUseCase<DeleteSessionInfoInput, UpdateSessionResult>() {
    override suspend operator fun invoke(input: DeleteSessionInfoInput): UpdateSessionResult {
        val id = storage.database.activitySessionQueries
            .getBySessionId(input.sessionId).executeAsOneOrNull()?.let {
                storage.database.activitySessionQueries.delete(it.id)
                it.id
            } ?: -1L
        return UpdateSessionResult(id)
    }
}
