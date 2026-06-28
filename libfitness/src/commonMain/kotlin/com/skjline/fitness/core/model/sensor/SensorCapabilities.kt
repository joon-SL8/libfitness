package com.skjline.fitness.core.model.sensor

import com.skjline.fitness.core.model.packet.Cadence
import com.skjline.fitness.core.model.packet.Power
import com.skjline.fitness.core.model.packet.Speed
import kotlinx.coroutines.flow.Flow

interface PowerObservable {
    val powerFlow: Flow<Power>
}

interface CadenceObservable {
    val cadenceFlow: Flow<Cadence>
}

interface SpeedObservable {
    val speedFlow: Flow<Speed>
}

interface PowerControllable {
    suspend fun setTargetPower(watts: Int): Result<Unit>
}
