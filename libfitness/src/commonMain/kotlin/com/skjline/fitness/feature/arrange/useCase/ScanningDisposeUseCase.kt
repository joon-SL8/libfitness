package com.skjline.fitness.feature.arrange.useCase

import com.skjline.fitness.injection.AppComponent
import com.skjline.fitness.feature.arrange.service.BluetoothSearchService
import org.koin.core.component.get

class ScanningDisposeUseCase {

    private val service = AppComponent.get<BluetoothSearchService>()

    operator fun invoke() {
        service.stopScanning()
    }
}