package com.skjline.fitness.data.asset

import java.io.ByteArrayInputStream
import java.io.InputStreamReader

const val ASSET_BASE_PATH = "training"

object ComposeAssetContentProvider {
    fun generateFileFromByteArray(byteArray: ByteArray): File {
        val header = File(ASSET_BASE_PATH, FileType.Dir, ASSET_BASE_PATH)
        val lines = InputStreamReader(ByteArrayInputStream(byteArray)).readLines()
        lines.forEach { line ->
            var child = header
            val path = line.split("/")
            path.forEach traverse@{ dir ->
                if (dir == "." || dir == ASSET_BASE_PATH) return@traverse
                child.children.firstOrNull { it.name == dir }?.let {
                    child = it
                } ?: run {
                    val type = if (dir.endsWith(".mrc")) {
                        FileType.File
                    } else {
                        FileType.Dir
                    }
                    child.children.add(
                        File(dir, type, line.substring(2))
                    )
                }
            }
        }
        return header
    }

    fun File.findChildrenOf(path: String) : List<String> {
        var base: File? = this
        val list = path.split("/")
        if (list.size > 1) {
            for(l in 1 until list.size) {
                val name = list.get(l)
                base?.children?.firstOrNull { it.name == name }.let {
                    base = it
                }
                base ?: break
            }
        }
        return base?.children?.map { it.name } ?: emptyList()
    }
}