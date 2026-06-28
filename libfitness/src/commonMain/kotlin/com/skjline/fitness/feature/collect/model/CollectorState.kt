package com.skjline.fitness.feature.collect.model

sealed interface CollectorState

data object Session : CollectorState
data object SearchMode : CollectorState
data object SessionPause : CollectorState
data object Initializing : CollectorState

data class SessionStart(val withERG: Boolean, val timestamp: Long) : CollectorState
data class SessionStop(val duration: Long) : CollectorState

data object Closing : CollectorState
