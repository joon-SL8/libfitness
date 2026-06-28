package com.skjline.fitness.feature.collect.useCase

import com.skjline.fitness.feature.collect.service.DataCollectionService

class GenerateActivitySessionUseCase(
    private val dataCollectionService: DataCollectionService
) {
    operator fun invoke() {
        // complete
        dataCollectionService.generateActivityFitFile()
    }
}