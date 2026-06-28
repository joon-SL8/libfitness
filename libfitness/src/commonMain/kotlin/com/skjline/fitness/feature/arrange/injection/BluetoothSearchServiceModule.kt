package com.skjline.fitness.feature.arrange.injection

import com.skjline.fitness.feature.arrange.service.BluetoothSearchService
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val bluetoothSearchServiceModule = module {
    singleOf<BluetoothSearchService>(::BluetoothSearchService)
}
