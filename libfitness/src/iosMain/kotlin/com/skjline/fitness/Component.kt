package com.skjline.fitness

import com.skjline.fitness.data.storage.StorageDatabase
import com.skjline.fitness.injection.AppComponent
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class Component {
    private val component: KoinComponent by lazy { AppComponent }

    fun getStorage(): StorageDatabase =
        component.get<StorageDatabase>()
}
