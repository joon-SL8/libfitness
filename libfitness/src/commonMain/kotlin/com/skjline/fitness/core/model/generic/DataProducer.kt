package com.skjline.fitness.core.model.generic

import com.skjline.fitness.core.model.packet.DataPacket

interface DataProducer<P : DataPacket>: DataCapture<P>