package com.skjline.fitness.data.storage.usecase

import com.skjline.fitness.core.model.workout.Session
import com.skjline.fitness.data.asset.model.GetSessionResult
import com.skjline.fitness.data.asset.model.UpdateSessionResult
import com.skjline.fitness.data.storage.input.DeleteSessionInfoInput
import com.skjline.fitness.data.storage.input.GetAllInput
import com.skjline.fitness.data.storage.input.GetSessionInfoInput
import com.skjline.fitness.data.storage.input.InsertSessionInfoInput
import com.skjline.fitness.data.storage.input.UpdateSessionPublishInput
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
class UpdateSessionUseCase : BaseDataUseCase<InsertSessionInfoInput, UpdateSessionResult>() {
    override suspend operator fun invoke(input: InsertSessionInfoInput): UpdateSessionResult {
        val session = input.data.takeIf { input.data.id == 0L }?.let {
            val id = storage.database.activitySessionQueries.getAll().executeAsList().size
            input.data.copy(id = (id + 1).toLong())
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
                sessionPublished = sessionPublished,
            )
        }
        return UpdateSessionResult(session.id)
    }
}

@OptIn(ExperimentalTime::class)
class UpdateSessionPublishedOnUseCase : BaseDataUseCase<UpdateSessionPublishInput, UpdateSessionResult>() {
    override suspend operator fun invoke(input: UpdateSessionPublishInput): UpdateSessionResult {
        val result = input.sessionId.takeIf { input.sessionId != 0L }?.let {
            println("UpdateSessionPublishedOnUseCase(${input.sessionId}): ${input.filename}")
            when {
                (input.timestamp != 0L) -> {
                    storage.database.activitySessionQueries.updateSessionPublish(
                        input.timestamp, input.sessionId
                    )
                    input.sessionId
                }

                (input.filename != "") -> {
                    storage.database.activitySessionQueries.updateSessionPublishFilename(
                        input.filename, input.sessionId
                    )
                    input.sessionId
                }

                else -> null
            }
        } ?: -1L

        return UpdateSessionResult(result)
    }
}

class GetAllSessionUseCase : BaseDataUseCase<GetAllInput, GetSessionResult>() {
    override suspend operator fun invoke(input: GetAllInput): GetSessionResult {
        val fetched = storage.database.activitySessionQueries.getAll().executeAsList()
        println("Get All Sessions: ${fetched.size}")
        val contents = fetched.map {
            val session = Session(
                id = it.id,
                name = it.name,
                description = it.description.orEmpty(),
                sessionDate = it.sessionDate,
                duration = it.duration,
                mrcFilename = it.mrcFilename,
                mrcFilepath = it.mrcFilepath,
                sessionFilename = it.sessionFilename,
                sessionPublished = it.sessionPublished ?: 0L,
            )

            val dt =
                Instant.fromEpochSeconds(it.sessionDate).toLocalDateTime(TimeZone.currentSystemDefault()).toString()
            val entities = storage.database.activityEntityQueries.getAllForSession(it.id).executeAsList()
            println("Get All Entities for Sessions(${it.id}|${it.name}): ${entities.size} ${it.sessionDate}($dt)")

            return@map session
        }

        return GetSessionResult(contents)
    }
}

class GetSessionUseCase : BaseDataUseCase<GetSessionInfoInput, GetSessionResult>() {
    override suspend operator fun invoke(input: GetSessionInfoInput): GetSessionResult {
        val contents = with(input) {
            id.takeIf { it != null && it > 0L }?.let { sessionId ->
                storage.database.activitySessionQueries.getBySessionId(sessionId).executeAsList()
            } ?: name?.let { sessionName ->
                storage.database.activitySessionQueries.getByName(sessionName).executeAsList()
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
                sessionPublished = it.sessionPublished ?: 0L,
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
