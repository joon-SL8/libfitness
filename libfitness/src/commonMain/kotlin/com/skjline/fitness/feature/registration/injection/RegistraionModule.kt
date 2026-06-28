package com.skjline.fitness.feature.registration.injection

import com.skjline.fitness.feature.registration.RegistrationViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val registrationModule = module {
    viewModel { RegistrationViewModel() }
}
