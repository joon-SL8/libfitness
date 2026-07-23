package com.skjline.fitness.core.model.generic

import com.skjline.fitness.core.model.workout.MrcCourse
import kotlinx.coroutines.flow.Flow

sealed interface Action

data object Initialize : Action

data object Connect : Action

data object Disconnect : Action

data object Start : Action

data object Resume : Action

data object Pause : Action

data object Stop : Action

data class SetCourse(val course: MrcCourse) : Action

data class SetTargetPower(val power: Int) : Action

data class SetTimeSubscriber(val subscriber: Flow<Long>) : Action
