package com.skjline.fitness.data.asset

sealed class FileType {
    data object Dir : FileType()
    data object File : FileType()
}

data class File(
    val name: String,
    val type: FileType = FileType.Dir,
    val full: String = "",
    val children: MutableSet<File> = mutableSetOf()
)
