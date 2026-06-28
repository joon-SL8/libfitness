package com.skjline.fitness.core.model.state

sealed interface Connectivity

data object Discovered : Connectivity
data object Connecting : Connectivity
data object Connected : Connectivity
data object Disconnecting : Connectivity
data object Disconnected : Connectivity
