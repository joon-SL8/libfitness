package com.skjline.fitness.data.storage.usecase

import com.skjline.fitness.data.asset.model.usrmsg.TAndC
import com.skjline.fitness.data.storage.StorageDatabase
import com.skjline.fitness.injection.AppComponent
import org.koin.core.component.inject
import kotlin.time.Instant

class SetTAndCStatusUseCase {
    private val storageDatabase: StorageDatabase by AppComponent.inject()

    operator fun invoke(type: TAndC, isAgreed: Boolean, dateAgreed: Long) {
        storageDatabase.database.tAndCAgreementQueries.upsertAgreement(
            type = type.name,
            isAgreed = isAgreed,
            dateAgreed = dateAgreed
        )
    }
}
