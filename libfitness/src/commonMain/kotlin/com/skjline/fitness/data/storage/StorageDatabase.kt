package com.skjline.fitness.data.storage

import com.skjline.fitness.core.model.generic.Const.Companion.EMPTY
import com.skjline.fitness.core.utils.DispatcherProvider
import com.skjline.fitness.injection.AppComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.core.component.inject
import kotlin.time.Clock.System.now
import kotlin.time.ExperimentalTime

// need to implement a method to persist the token and/or create token with additional security
@OptIn(ExperimentalTime::class)
class StorageDatabase {
    private val dispatcherProvider by AppComponent.inject<DispatcherProvider>()

    lateinit var database: FitnessDatabase
        private set

    fun isReady(): Boolean = this::database.isInitialized

    fun initialize() {
        if (isReady()) {
            println("database is already initialized!!!")
            return
        }

        CoroutineScope(dispatcherProvider.io).launch {
            println("database initialize")
            val sqlDriver = provideDBDriver(FitnessDatabase.Schema)
            database = FitnessDatabase(sqlDriver)
        }
    }

    fun getUserProfile(profile: String): Map<String, String> {
        val profiles = database.userProfileQueries.getAll().executeAsList()
        return if (profile.isEmpty()) {
            profiles.associate { Pair(it.name, it.data_) }
        } else {
            val content = profiles.firstOrNull { it.name == profile }?.data_ ?: EMPTY
            mapOf(profile to content)
        }
    }

    fun insertOrUpdateUserProfile(profile: String, content: String) {
        val profiles = database.userProfileQueries.getAll().executeAsList()
        val data = profiles.firstOrNull { it.name == profile }
        if (data == null || profile.isEmpty()) {
            database.userProfileQueries
                .insert(
                    id = profiles.size.toLong() + 1L,
                    name = profile,
                    data_ = content,
                    updated = now().toEpochMilliseconds(),
                )
        } else {
            database.userProfileQueries
                .updateData(
                    data = content,
                    id = data.id,
                )
        }
    }
}