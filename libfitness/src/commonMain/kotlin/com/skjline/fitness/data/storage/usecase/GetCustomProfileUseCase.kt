package com.skjline.fitness.data.storage.usecase

import com.skjline.fitness.data.asset.model.GetDataResult
import com.skjline.fitness.data.storage.input.GetProfileInput
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
class GetCustomProfileUseCase : BaseGetDataUseCase<GetProfileInput>() {
    override suspend operator fun invoke(input: GetProfileInput): GetDataResult {
        return getData(input.key)
    }
}
