import com.skjline.fitness.data.asset.ComposeAssetContentProvider
import com.skjline.fitness.data.asset.ComposeAssetContentProvider.findChildrenOf
import com.skjline.fitness.data.asset.File
import com.skjline.fitness.core.utils.DispatcherProvider
import com.skjline.fitness.injection.AppComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import com.skjline.fitness.resources.Res
import okio.Buffer
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.koin.core.component.get

const val STRUCTURE  = "files/structure"

val dispatcherProvider = AppComponent.get<DispatcherProvider>()

var file: File? = null

@OptIn(ExperimentalResourceApi::class)
actual val trainingFilesProvider: AssetFileProvider = AssetFileProvider { path ->
    Buffer().also { it.write(Res.readBytes("files/$path")) }
}

@OptIn(ExperimentalResourceApi::class)
actual val trainingPathProvider: AssetPathProvider = AssetPathProvider { path ->
    file?.findChildrenOf(path) ?: run {
        CoroutineScope(dispatcherProvider.io).launch {
            Res.readBytes(STRUCTURE).let { raw ->
                file = ComposeAssetContentProvider.generateFileFromByteArray(raw)
            }
        }
        listOf("beginner", "ss-base", "time-crunched-ftp-builder")
    }
}
