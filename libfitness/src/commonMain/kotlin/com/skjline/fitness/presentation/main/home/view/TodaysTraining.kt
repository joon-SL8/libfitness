package com.skjline.fitness.presentation.main.home.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.skjline.fitness.core.model.workout.MrcCourse
import com.skjline.fitness.presentation.shared.TodayTrainingView
import com.skjline.fitness.presentation.shared.style.Dimens
import com.skjline.fitness.presentation.shared.style.accent
import com.skjline.fitness.presentation.shared.style.background_light
import com.skjline.fitness.presentation.shared.style.bg_decorator_color
import com.skjline.fitness.presentation.shared.style.font_light
import com.skjline.fitness.resources.Res
import com.skjline.fitness.resources.todays_training
import com.skjline.fitness.resources.todays_training_start
import org.jetbrains.compose.resources.stringResource

@Composable
fun TodaysTraining(course: MrcCourse? = null) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(
                start = Dimens.Padding.Normal.asDP(),
                end = Dimens.Padding.Normal.asDP(),
                top = Dimens.Padding.Normal.asDP()
            ),
    ) {
        Text(
            text = stringResource(Res.string.todays_training),
            style = MaterialTheme.typography.titleMedium,
            fontSize = 16.sp,
            maxLines = 1,
        )
        course?.let {
            TodayTrainingView(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = Dimens.Padding.Normal.asDP())
                    .height(Dimens.Size.DayHeight.asDP()),
                course = it,
                fg = accent,
            )
        }
        Button(
            colors = ButtonColors(
                containerColor = accent,
                contentColor = font_light,
                disabledContainerColor = background_light,
                disabledContentColor = bg_decorator_color,
            ),
            onClick = {
                println("Start NOW!!!")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = Dimens.Padding.Normal.asDP())
                .height(Dimens.Size.Button.asDP())
        ) {
            Text(
                text = stringResource(Res.string.todays_training_start),
                style = MaterialTheme.typography.labelLarge,
            )
        }
    }
}