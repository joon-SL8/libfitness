package com.skjline.fitness.data.storage.usecase

import com.skjline.fitness.data.asset.model.DataResult
import com.skjline.fitness.data.asset.model.ErrorUpdatObject
import com.skjline.fitness.data.asset.model.UpdateDeviceResult
import com.skjline.fitness.data.storage.input.InsertDeviceInput
import com.skjline.fitness.data.storage.model.map

class AddDeviceUseCase : BaseDataUseCase<InsertDeviceInput, DataResult>() {
    override suspend operator fun invoke(input: InsertDeviceInput): DataResult {
        if (input !is InsertDeviceInput) {
            return ErrorUpdatObject("Insert device failed - incorrect input type $input")
        }

        val device = input.device.takeIf {
            it.id.isEmpty()
        }?.let { dev ->
            // insert new item
            val max = storage.database.deviceQueries
                .getMaxId().executeAsOneOrNull() ?: 0
            input.device.copy(id = dev.id)
        } ?: input.device

        storage.database.deviceQueries
            .insertDevice(device.map())
        return UpdateDeviceResult
    }
}
