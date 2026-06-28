import okio.FileSystem
import okio.Path.Companion.toPath
import platform.Foundation.NSBundle

const val IOS_ASSET_BASE_PATH =
    "/Frameworks/libfitness.framework/composeResources/com.skjline.fitness.resources/files/training/"

actual val trainingFilesProvider: AssetFileProvider = AssetFileProvider { path ->
    val assetFile = NSBundle.mainBundle.resourcePath + IOS_ASSET_BASE_PATH + path
    FileSystem.SYSTEM.source(assetFile.toPath())
}

actual val trainingPathProvider: AssetPathProvider = AssetPathProvider { path ->
    val assetBasePath = NSBundle.mainBundle.resourcePath + IOS_ASSET_BASE_PATH
    val content = FileSystem.SYSTEM.listOrNull((assetBasePath + path).toPath())

    println("get from ios path: ${assetBasePath + path} - ${content?.size}")
    content?.map { it.toString() } ?: emptyList()
}
