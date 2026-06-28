package com.skjline.fitness.data.asset.useCase

import AssetFileProvider
import com.skjline.fitness.injection.AppComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.buffer
import okio.use
import org.koin.core.component.inject

class AssetFilePathUseCase {
    private val assetFileProvider: AssetFileProvider by AppComponent.inject()

    operator fun invoke(assetFilePath: String): Flow<String> = flow {
        assetFileProvider.get(assetFilePath).buffer().use { data ->
            emit(data.readByteArray().decodeToString())
        }
    }
}
