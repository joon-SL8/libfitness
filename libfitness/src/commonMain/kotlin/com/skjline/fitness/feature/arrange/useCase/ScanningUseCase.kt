package com.skjline.fitness.feature.arrange.useCase

import com.skjline.fitness.injection.AppComponent
import com.skjline.fitness.feature.arrange.service.BluetoothSearchService
import org.koin.core.component.get

class ScanningUseCase {

    private val service = AppComponent.get<BluetoothSearchService>()

    val searchDeviceState = service.scanDeviceState

    operator fun invoke() {
        service.startScanning()
    }
}