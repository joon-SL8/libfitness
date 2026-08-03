import com.skjline.fitness.feature.publish.fit.encoder.FitProcessor
import com.skjline.fitness.feature.publish.fit.injection.provideFitProcessor
import com.skjline.fitness.data.storage.StorageDatabase
import com.skjline.fitness.injection.AppComponent
import com.skjline.fitness.injection.appModule
import com.skjline.fitness.DefaultLauncher
import org.koin.core.KoinApplication
import org.koin.core.component.get
import org.koin.core.context.startKoin
import org.koin.dsl.module

/**
 * Initializes Koin for client application
 * A convenience method to avoid setting block, a high order method
 *
 * @param processor Fit file processor
 * @param launcher Screen Launcher / Navigator
 */
fun initializeApp(
    processor: FitProcessor,
) = initializeApp(
    processor,
) {}

/**
 * Initializes Koin for client application
 * A convenience method to avoid setting block, a high order method
 *
 * @param processor Fit file processor
 * @param launcher Screen Launcher / Navigator
 */
fun initializeApp(
    processor: FitProcessor,
    launcher: Launcher,
) = initializeApp(
    processor,
    launcher,
) {}

/**
 * Initializes Koin for client application
 * A convenience method to avoid setting block, a high order method
 *
 * @param processor Fit file processor
 */
fun simpleInitializer(
    processor: FitProcessor,
) = initializeApp(
    processor,
    launcher = DefaultLauncher(),
) {

}

/**
 * Initializes Koin for client application
 *
 * Note: Android requires to set Context thus need `block` that runs first
 * particularly for old devices (emulator)
 *
 * @param processor Fit file processor
 * @param launcher Screen Launcher / Navigator
 * @param block KoinApplication invoke
 */
fun initializeApp(
    processor: FitProcessor,
    launcher: Launcher = DefaultLauncher(),
    block: KoinApplication.() -> Unit = {}
): KoinApplication = startKoin {
    block()
    modules(
        appModule() + module {
            single<Launcher> { launcher }
        } + provideFitProcessor(processor)
    )
}.apply {
    AppComponent.get<StorageDatabase>().initialize()
}
