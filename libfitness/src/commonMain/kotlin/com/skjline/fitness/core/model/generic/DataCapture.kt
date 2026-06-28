package com.skjline.fitness.core.model.generic

import com.skjline.fitness.core.model.packet.DataPacket
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface DataCapture<P : DataPacket> {
    fun observeDataPacket(): SharedFlow<P>
    fun getProvidingTypes(): List<PacketType>

    fun request(action: Action)
}
