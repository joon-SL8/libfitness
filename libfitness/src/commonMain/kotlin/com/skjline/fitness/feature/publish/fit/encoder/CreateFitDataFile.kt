package com.skjline.fitness.feature.publish.fit.encoder

import com.skjline.fitness.feature.publish.fit.model.FitContent

sealed class FileCreateResult {
    // returns success with file location
    data class Success(val fileUrl: String) : FileCreateResult()
    data class Failed(val error: String) : FileCreateResult()
}

interface FitProcessor {
    fun processFitFileData(filename: String, content: FitContent): FileCreateResult
    fun loadFitFile(filename: String): ByteArray
}
