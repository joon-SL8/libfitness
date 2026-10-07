package com.skjline.fitness.data.storage

import com.skjline.fitness.core.utils.DispatcherProvider
import com.skjline.fitness.injection.AppComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.core.component.inject
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
}