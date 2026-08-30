package com.skjline.fitness.data.storage.usecase

import com.skjline.fitness.data.asset.model.usrmsg.TAndC
import com.skjline.fitness.data.storage.StorageDatabase
import com.skjline.fitness.data.storage.model.TAndCAgreement
import com.skjline.fitness.injection.AppComponent
import org.koin.core.component.inject

class GetTAndCStatusUseCase {
    private val storageDatabase: StorageDatabase by AppComponent.inject()

    operator fun invoke(type: TAndC): TAndCAgreement? {
        val entity = storageDatabase.database.tAndCAgreementQueries
            .getAgreement(type.name)
            .executeAsOneOrNull()
        
        return entity?.let {
            TAndCAgreement(
                type = it.type,
                isAgreed = it.isAgreed,
                dateAgreed = it.dateAgreed ?: 0L
            )
        }
    }
}
