package com.skjline.fitness.injection

import com.skjline.fitness.data.asset.injection.assetProviderModule
import com.skjline.fitness.feature.registration.injection.registrationModule
import com.skjline.fitness.feature.arrange.injection.bluetoothSearchServiceModule
import com.skjline.fitness.feature.collect.injection.activityDataCollectionModule
import com.skjline.fitness.data.storage.injection.dataStorageModule
import com.skjline.fitness.feature.publish.strava.injection.apiClientModule

fun appModule() =
    listOf(
        platformModule,
        apiClientModule,
        dataStorageModule,
        assetProviderModule,
        registrationModule,
        bluetoothSearchServiceModule,
        activityDataCollectionModule,
    )
