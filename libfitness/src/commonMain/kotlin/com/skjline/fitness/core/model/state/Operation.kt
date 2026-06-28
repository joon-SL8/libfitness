package com.skjline.fitness.core.model.state

import com.skjline.fitness.core.model.sensor.BLESensor

sealed interface Operation

data object ScanError : Operation
data object Init : Operation

data class OnDeviceUpdated(val devices: List<BLESensor<*>>) : Operation
data class OnDeviceCollectionUpdated(val devices: List<BLESensor<*>>) : Operation
