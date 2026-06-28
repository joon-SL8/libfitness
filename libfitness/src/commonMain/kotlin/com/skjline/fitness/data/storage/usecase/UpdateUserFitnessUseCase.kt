package com.skjline.fitness.data.storage.usecase

import com.skjline.fitness.core.utils.DispatcherProvider
import com.skjline.fitness.data.asset.model.UpdateFitnessResult
import com.skjline.fitness.data.storage.Constants.Companion.PROFILE_KEY_FTP
import com.skjline.fitness.data.storage.Constants.Companion.PROFILE_KEY_WEIGHT
import com.skjline.fitness.data.storage.UserProfileEntity
import com.skjline.fitness.data.storage.input.FitnessInput
import com.skjline.fitness.injection.AppComponent
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.get
import kotlin.time.Clock.System.now
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
class UpdateUserFitnessUseCase : BaseDataUseCase<FitnessInput, UpdateFitnessResult>() {
    override suspend operator fun invoke(input: FitnessInput): UpdateFitnessResult {
        val contents = storage.database.userProfileQueries
            .getAll().executeAsList()

        val index = contents.size
        val dispatcher = AppComponent.get<DispatcherProvider>()
        withContext(dispatcher.io) {
            listOf(
                launch { insertOrUpdateData(index + 1, input.ftp, PROFILE_KEY_FTP, contents) },
                launch { insertOrUpdateData(index + 2, input.weight, PROFILE_KEY_WEIGHT, contents) }
            ).joinAll()
        }

        return UpdateFitnessResult
    }

    private fun insertOrUpdateData(index: Int, data: Int, type: String, contents: List<UserProfileEntity>) {
        contents.findLast {
            it.name == type
        }?.let { fitness ->
            println("updating $type:$data")
            storage.database.userProfileQueries.updateData(id = fitness.id, data = "$data")
        } ?: run {
            println("adding[$index] $type:$data")
            storage.database.userProfileQueries.insert(
                id = index.toLong(),
                name = type,
                data_ = "$data",
                updated = now().epochSeconds,
            )
        }
    }
}
