package com.skjline.fitness.feature.registration.model


sealed interface RegStep

data object SetLyncSignIn: RegStep

data object ApplyCredential: RegStep
data object SetFitness: RegStep
data object SetProfile: RegStep
data object SetAthleteProfile: RegStep
data object Completed: RegStep
data object Finish: RegStep
