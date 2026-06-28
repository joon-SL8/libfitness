import com.skjline.fitness.presentation.Route

interface Platform {
    val name: String
}

interface Launcher {
    fun launch(route: Route)
}

expect fun getPlatform(): Platform
