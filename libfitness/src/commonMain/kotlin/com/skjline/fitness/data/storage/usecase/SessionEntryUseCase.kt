package com.skjline.fitness.data.storage.usecase

import com.skjline.fitness.core.model.workout.SessionEntry
import com.skjline.fitness.data.asset.model.GetSessionEntriesResult
import com.skjline.fitness.data.asset.model.UpdateSessionResult
import com.skjline.fitness.data.storage.input.GetSessionEntryInput
import com.skjline.fitness.data.storage.input.InsertSessionEntryInput
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
class UpdateSessionEntryUseCase : BaseDataUseCase<InsertSessionEntryInput, UpdateSessionResult>() {
    override suspend operator fun invoke(input: InsertSessionEntryInput): UpdateSessionResult {
        val entry = input.data.takeIf { it.id == 0L }?.let {
            val id = storage.database.activityEntityQueries.getMaxId().executeAsOneOrNull() ?: 0L
            it.copy(id = id + 1)
        } ?: input.data
        with(entry) {
            storage.database.activityEntityQueries.insert(
                id = id,
                session = session,
                name = name,
                description = description,
                start = start,
                duration = duration,
                power = power,
                heart = heart,
                speed = speed,
                cadence = cadence,
            )
        }
        return UpdateSessionResult(entry.id)
    }
}

class GetSessionEntryUseCase : BaseDataUseCase<GetSessionEntryInput, GetSessionEntriesResult>() {
    override suspend operator fun invoke(input: GetSessionEntryInput): GetSessionEntriesResult {
        val contents = storage.database
            .activityEntityQueries.getAllForSession(input.sessionId)
            .executeAsList().map {
                SessionEntry(
                    id = it.id,
                    session = it.session,
                    name = it.name,
                    description = it.description.orEmpty(),
                    start = it.start,
                    duration = it.duration,
                    power = it.power ?: 0L,
                    heart = it.heart ?: 0L,
                    speed = it.speed ?: 0L,
                    cadence = it.cadence ?: 0L,
                )
            }

        return GetSessionEntriesResult(contents)
    }
}

class DeleteSessionEntryUseCase : BaseDataUseCase<GetSessionEntryInput, UpdateSessionResult>() {
    override suspend operator fun invoke(input: GetSessionEntryInput): UpdateSessionResult {
        val id = storage.database.activityEntityQueries
            .getAllForSession(input.sessionId).executeAsOneOrNull()?.id ?: -1L
        if (id != -1L) {
            storage.database.activityEntityQueries.delete(input.sessionId)
        }
        return UpdateSessionResult(id)
    }
}

