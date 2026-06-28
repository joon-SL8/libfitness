package com.skjline.fitness.data.asset.useCase

import AssetPathProvider
import com.skjline.fitness.data.asset.model.AssetProperty
import com.skjline.fitness.injection.AppComponent
import org.koin.core.component.inject

class AssetPathProviderUseCase {
    private val assetPathProvider: AssetPathProvider by AppComponent.inject()

    operator fun invoke(assetFilePath: String = BASE): List<AssetProperty> {
        println("search path: $assetFilePath")

        val list = assetPathProvider.get(assetFilePath)
        val assets = list.map {
            println("asset: $it")

            val type = if (it.lowercase().endsWith(MRC)) {
                AssetProperty.Type.File
            } else {
                AssetProperty.Type.Dir
            }
            val last = it.lastIndexOf("/") + 1
            val path = it.substring(last)
            AssetProperty(type = type, data = path)
        }
        return assets
    }

    private companion object {
        const val BASE = "training"
        const val MRC = ".mrc"
    }
}
