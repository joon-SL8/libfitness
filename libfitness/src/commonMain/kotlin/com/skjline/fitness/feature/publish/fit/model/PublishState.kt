package com.skjline.fitness.feature.publish.fit.model

import kotlinx.serialization.Serializable

sealed interface PublishState

@Serializable
data class OnUploadReady(val filename: String)

data object OnUploadFailed : PublishState
data object OnUploadSucceed : PublishState

data object Initial : PublishState
data class DataUploadReady(val param: OnUploadReady) : PublishState
data class DataUploadComplete(val result: PublishState) : PublishState
