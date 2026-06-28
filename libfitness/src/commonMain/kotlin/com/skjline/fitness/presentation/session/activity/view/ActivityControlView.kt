package com.skjline.fitness.presentation.session.activity.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.skjline.fitness.core.model.workout.MrcCourse
import com.skjline.fitness.feature.collect.model.CollectorState
import com.skjline.fitness.feature.collect.model.Session
import com.skjline.fitness.feature.collect.model.SessionStart
import com.skjline.fitness.feature.collect.service.DataCollectionService
import com.skjline.fitness.presentation.shared.style.Dimens
import com.skjline.fitness.presentation.shared.style.bg_train
import com.skjline.fitness.presentation.shared.style.font_light
import com.skjline.fitness.presentation.shared.style.ftp_bg_color
import com.skjline.fitness.resources.Res
import com.skjline.fitness.resources.erg
import com.skjline.fitness.resources.pause
import com.skjline.fitness.resources.start
import com.skjline.fitness.resources.stop
import org.jetbrains.compose.resources.stringResource

@Composable
fun ActivityControlView(
    startItemModifier: Modifier,
    course: MrcCourse,
    sessionStatus: CollectorState,
    dataCollectionService: DataCollectionService,
) {
    val ergMode = remember { mutableStateOf(false) }
    val colors = if (sessionStatus == Session) {
        ButtonDefaults.buttonColors(containerColor = bg_train)
    } else {
        ButtonDefaults.buttonColors()
    }

    val sessionStarted = sessionStatus is SessionStart
    if (sessionStarted) {
        ergMode.value = sessionStatus.withERG
        println("start session with erg mode: ${ergMode.value}")
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Row(
            modifier = Modifier.wrapContentWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Button(
                modifier = startItemModifier.align(Alignment.CenterVertically),
                shape = MaterialTheme.shapes.large,
                colors = colors,
                content = {
                    Text(
                        text = stringResource(
                            resource = if (sessionStarted) {
                                Res.string.pause
                            } else {
                                Res.string.start
                            }
                        ),
                        style = MaterialTheme.typography.labelMedium,
                    )
                },
                onClick = {
                    dataCollectionService.beginSession(course)
                }
            )

            if (sessionStarted) {
                Button(
                    modifier = startItemModifier.align(Alignment.CenterVertically)
                        .padding(start = Dimens.Padding.XSmall.asDP()),
                    shape = MaterialTheme.shapes.large,
                    colors = colors,
                    content = {
                        Text(
                            text = stringResource(resource = Res.string.stop),
                            style = MaterialTheme.typography.labelMedium,
                        )
                    },
                    onClick = {
                        dataCollectionService.stopSession()
                    }
                )
            }
        }

        if (sessionStarted) {
            Button(
                modifier = startItemModifier.align(Alignment.CenterVertically),
                shape = MaterialTheme.shapes.large,
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (ergMode.value) bg_train else ftp_bg_color,
                    contentColor = font_light,
                ),
                content = {
                    Text(
                        text = stringResource(resource = Res.string.erg),
                        style = MaterialTheme.typography.labelMedium,
                    )
                },
                onClick = {
                    println("changing the erg mode")
                    if (ergMode.value) {
                        dataCollectionService.disableErgMode()
                    } else {
                        dataCollectionService.enableErgMode()
                    }
                    ergMode.value = dataCollectionService.isErgModeEnabled()
                    println("updated erg mode: ${ergMode.value}")
                }
            )
        }
    }
}
