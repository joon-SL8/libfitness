package com.skjline.fitness.feature.registration.model


sealed interface Action

data class Next(val input: Input): Action
data class Register(val input: Input): Action
data object Exit: Action
data object Cancel: Action
