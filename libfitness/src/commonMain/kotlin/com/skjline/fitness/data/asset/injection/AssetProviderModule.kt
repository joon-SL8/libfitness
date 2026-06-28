package com.skjline.fitness.data.asset.injection

import AssetFileProvider
import AssetPathProvider
import org.koin.dsl.module
import trainingFilesProvider
import trainingPathProvider

val assetProviderModule = module {
    single<AssetFileProvider> { trainingFilesProvider }
    single<AssetPathProvider> { trainingPathProvider }
}