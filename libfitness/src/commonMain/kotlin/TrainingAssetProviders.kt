import okio.Source

fun interface AssetFileProvider {
    suspend fun get(path: String): Source
}

fun interface AssetPathProvider {
    fun get(path: String): List<String>
}

expect val trainingFilesProvider: AssetFileProvider

expect val trainingPathProvider: AssetPathProvider
