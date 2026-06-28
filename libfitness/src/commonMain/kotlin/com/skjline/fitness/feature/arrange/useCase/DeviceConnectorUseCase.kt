package com.skjline.fitness.feature.arrange.useCase

import com.skjline.fitness.injection.AppComponent
import com.skjline.fitness.core.model.sensor.BLESensor
import com.skjline.fitness.feature.arrange.service.BluetoothSearchService
import org.koin.core.component.get

class DeviceConnectorUseCase {
    private val service = AppComponent.get<BluetoothSearchService>()

    operator fun invoke(devices: List<String>): List<BLESensor<*>> =
        service.collectConnectedDevice(devices)
}