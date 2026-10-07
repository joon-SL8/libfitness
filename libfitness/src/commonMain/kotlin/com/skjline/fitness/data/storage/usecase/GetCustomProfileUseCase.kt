package com.skjline.fitness.data.storage.usecase

import com.skjline.fitness.data.asset.model.GetDataResult
import com.skjline.fitness.data.storage.input.GetProfileInput

class GetCustomProfileUseCase : BaseGetDataUseCase<GetProfileInput>() {
    override suspend operator fun invoke(input: GetProfileInput): GetDataResult {
        val data: GetDataResult = getData(input.key)
        data.data.let { content ->
            println("GetCustomProfileUseCase (key: ${input.key}): $content")
        }
        return data
    }
}
