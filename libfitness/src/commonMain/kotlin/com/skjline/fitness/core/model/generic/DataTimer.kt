package com.skjline.fitness.core.model.generic

import com.skjline.fitness.core.model.packet.DataPacket
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

interface DataTimer<P : DataPacket> : DataCapture<P>
