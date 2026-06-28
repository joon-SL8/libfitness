package com.skjline.fitness.presentation.shared.calendar

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.skjline.fitness.presentation.shared.calendar.view.DeviceView
import com.skjline.fitness.presentation.shared.calendar.view.SessionView

@Composable
fun CalendarScreen() {

    DeviceView(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    )
    SessionView(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    )
}