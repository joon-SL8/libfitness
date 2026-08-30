package com.skjline.fitness.data.storage.usecase

import com.skjline.fitness.core.model.ble.BluetoothComponent
import com.skjline.fitness.data.asset.model.GetDeviceResult
import com.skjline.fitness.data.storage.input.GetDeviceInput

class GetDeviceUseCase: BaseDataUseCase<GetDeviceInput, GetDeviceResult>() {
    override suspend operator fun invoke(input: GetDeviceInput): GetDeviceResult {
        return GetDeviceResult(BluetoothComponent())
    }
}
