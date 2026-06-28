package com.skjline.fitness.feature.collect.service

import com.skjline.fitness.core.model.packet.DataPacket
import com.skjline.fitness.core.model.generic.DataCapture
import com.skjline.fitness.core.model.workout.MrcCourse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector

interface ActivityDataCollector {

    fun addCollector(collector: DataCapture<*>)
    fun collect(): Flow<DataPacket>

    fun beginSession(course: MrcCourse)
    fun pauseSession()
    fun stopSession()

    fun enableErgMode()
    fun disableErgMode()

    fun updatePowerOffset(power: Int): Int
}
