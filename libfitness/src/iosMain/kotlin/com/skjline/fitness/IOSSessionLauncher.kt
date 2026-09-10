package com.skjline.fitness

import com.skjline.fitness.injection.AppComponent
import com.skjline.fitness.presentation.DismissActivity
import com.skjline.fitness.presentation.DismissModal
import com.skjline.fitness.presentation.MainRoute
import com.skjline.fitness.presentation.RegistrationRoute
import com.skjline.fitness.presentation.Route
import com.skjline.fitness.presentation.SessionRoute
import com.skjline.fitness.presentation.ShowModal
import mainViewController
import org.koin.core.component.KoinComponent
import platform.UIKit.UIApplication
import platform.UIKit.UIModalPresentationFullScreen
import registrationViewController
import sessionViewController

class IOSSessionLauncher : Launcher {
    val koinProvider : KoinComponent by lazy {
        AppComponent
    }

    override fun launch(route: Route) {

        val invoker = when (route) {
            is MainRoute -> mainViewController()
            is RegistrationRoute -> registrationViewController()
            is SessionRoute -> sessionViewController(route.path)
            is ShowModal -> {
                UIApplication.sharedApplication.keyWindow?.rootViewController
                    ?.dismissViewControllerAnimated(flag = true) { }
                return
            }
            is DismissModal -> return
            is DismissActivity -> return
        }

        UIApplication.sharedApplication.keyWindow?.rootViewController?.let {
            it.dismissViewControllerAnimated(flag = true) {
                val vc = invoker.apply {
                    modalPresentationStyle = UIModalPresentationFullScreen
                }

                UIApplication.sharedApplication.idleTimerDisabled = true
                it.showViewController(vc = vc, sender = null)
            }
        } ?: run {
            println("unable to capture root view controller")
        }
    }
}
