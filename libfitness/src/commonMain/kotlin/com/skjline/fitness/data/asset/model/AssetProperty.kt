package com.skjline.fitness.data.asset.model

data class AssetProperty(
    val type: Type,
    val data: Any,
) {
    enum class Type {
        Dir, File, Up, Select,
    }
}
