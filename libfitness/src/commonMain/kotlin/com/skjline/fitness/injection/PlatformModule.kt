package com.skjline.fitness.injection

import com.skjline.fitness.core.Platform
import com.skjline.fitness.core.getPlatform
import com.skjline.fitness.core.security.EncryptedPropertyManager
import com.skjline.fitness.core.security.EncryptionHandler
import com.skjline.fitness.core.security.EncryptionProcessor
import com.skjline.fitness.core.security.getPropertyManager
import com.skjline.fitness.core.utils.DispatcherProvider
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val privateSkjlineModule = module {
    single<EncryptionProcessor> {
        EncryptionHandler("1234567890abcdef", "12345678")
    }
    single<EncryptedPropertyManager> {
        getPropertyManager()
    }
}

val platformModule = module {
    includes(privateSkjlineModule)

    singleOf<Platform>(::getPlatform)
    singleOf<DispatcherProvider>(::DispatcherProvider)
}
