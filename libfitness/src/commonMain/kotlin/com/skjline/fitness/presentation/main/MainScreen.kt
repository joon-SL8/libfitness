package com.skjline.fitness.presentation.main

import com.skjline.fitness.Launcher
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.skjline.fitness.core.model.generic.Const.Companion.EMPTY
import com.skjline.fitness.injection.AppComponent
import com.skjline.fitness.presentation.SessionRoute
import com.skjline.fitness.presentation.main.profile.ScreenProfile
import com.skjline.fitness.presentation.main.plan.ScreenWorkout
import com.skjline.fitness.resources.Res
import com.skjline.fitness.resources.ic_bike
import com.skjline.fitness.resources.ic_library
import com.skjline.fitness.resources.ic_person
import org.jetbrains.compose.resources.vectorResource
import org.koin.core.component.get

@Composable
fun MainScreen(link: String = EMPTY) {
    MaterialTheme {
        val sessionLauncher = AppComponent.get<Launcher>()

//        Navigator(screen = ScreenHome(), onBackPressed = { true }) { nav ->
        Navigator(screen = ScreenWorkout(), onBackPressed = { true }) { nav ->
            Scaffold(
                bottomBar = {
                    BottomAppBar(
                        actions = {
//                            IconButton(onClick = { nav.push(ScreenHome()) }) {
//                                Icon(
//                                    imageVector = vectorResource(Res.drawable.ic_home),
//                                    contentDescription = null,
//                                )
//                            }

                            IconButton(onClick = { nav.push(ScreenWorkout()) }) {
                                Icon(
                                    imageVector = vectorResource(Res.drawable.ic_library),
                                    contentDescription = null,
                                )
                            }

                            IconButton(onClick = { nav.push(ScreenProfile()) }) {
                                Icon(
                                    imageVector = vectorResource(Res.drawable.ic_person),
                                    contentDescription = null,
                                )
                            }
                        },
                        floatingActionButton = {
                            FloatingActionButton(onClick = { sessionLauncher.launch(SessionRoute(EMPTY)) }) {
                                Icon(
                                    imageVector = vectorResource(Res.drawable.ic_bike),
                                    contentDescription = null,
                                )
                            }
                        }
                    )
                }
            ) {
                SlideTransition(modifier = Modifier.padding(it), navigator = nav)
            }
        }
    }
}
