package com.skjline.fitness.feature.arrange.useCase

import com.skjline.fitness.core.model.packet.DataPacket
import com.skjline.fitness.injection.AppComponent
import com.skjline.fitness.core.model.ble.BluetoothComponent
import com.skjline.fitness.feature.arrange.service.BluetoothSearchService
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.get

class DeviceConnectionUseCase {

    private val service = AppComponent.get<BluetoothSearchService>()

    operator fun invoke(select: Boolean, device: BluetoothComponent): Flow<DataPacket>? {
        val device = if (select) {
            service.connectToDevice(device)
        } else {
            service.disconnectToDevices(device)
        }

        return  device
    }
}