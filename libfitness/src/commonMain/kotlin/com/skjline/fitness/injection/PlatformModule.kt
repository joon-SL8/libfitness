package com.skjline.fitness.injection

import Platform
import com.skjline.fitness.core.security.EncryptedPropertyManager
import com.skjline.fitness.core.security.getPropertyManager
import com.skjline.fitness.core.utils.DispatcherProvider
import getPlatform
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val privateSkjlineModule = module {
    single<EncryptedPropertyManager> { getPropertyManager() }
}

val platformModule = module {
    includes(privateSkjlineModule)

    singleOf<Platform>(::getPlatform)
    singleOf<DispatcherProvider>(::DispatcherProvider)
//    single<Launcher> { getSessionScreenLauncher() }
}
