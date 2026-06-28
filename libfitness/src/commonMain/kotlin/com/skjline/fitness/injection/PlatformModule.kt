package com.skjline.fitness.injection

import Platform
import com.skjline.fitness.core.utils.DispatcherProvider
import getPlatform
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val platformModule = module {
    singleOf<Platform>(::getPlatform)
    singleOf<DispatcherProvider>(::DispatcherProvider)
//    single<Launcher> { getSessionScreenLauncher() }
}
