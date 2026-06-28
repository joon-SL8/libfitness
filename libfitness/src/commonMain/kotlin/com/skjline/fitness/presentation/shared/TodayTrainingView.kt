package com.skjline.fitness.presentation.shared

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import com.skjline.fitness.core.model.workout.MrcCourse
import com.skjline.fitness.data.asset.model.AssetProperty
import com.skjline.fitness.presentation.main.plan.view.WorkoutDetailView
import com.skjline.fitness.presentation.shared.style.Dimens
import com.skjline.fitness.presentation.shared.style.accent
import com.skjline.fitness.presentation.shared.style.bg_decorator_color
import com.skjline.fitness.presentation.shared.style.bg_status_pane
import com.skjline.fitness.presentation.shared.style.bg_train

@Composable
fun TodayTrainingView(
    modifier: Modifier = Modifier.fillMaxSize(),
    course: MrcCourse,
    fg: Color = accent,
    onClickListener: (AssetProperty) -> Unit = {},
) {

    Box(
        modifier = modifier
            .clickable {
                onClickListener(
                    AssetProperty(type = AssetProperty.Type.Select, data = course.filename)
                )
            },
    ) {
        Image(
            modifier = Modifier
                .height(IntrinsicSize.Min)
                .fillMaxWidth(.35f)
                .align(Alignment.CenterStart)
                .background(
                    bg_train,
                    RoundedCornerShape(Dimens.RoundedCorner.Normal.asDP())
                ),
            bitmap = course.getAsImage(fg),
            contentScale = ContentScale.FillBounds,
            contentDescription = ""
        )

        Row(
            modifier = Modifier
                .fillMaxWidth(.65f)
                .height(IntrinsicSize.Min)
                .align(Alignment.CenterEnd)
                .padding(start = Dimens.Padding.XSmall.asDP())
                .background(
                    bg_status_pane,
                    RoundedCornerShape(Dimens.RoundedCorner.MegaLarge.asDP())
                ),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            val min = course.course.last().first.toInt()
            val map = mapOf("Duration" to "$min min", "Stress" to "-", "IF" to "-")
            map.onEachIndexed { index, message ->
                WorkoutDetailView(
                    label = message.key,
                    message = message.value,
                )
                if (index < map.size - 1) {
                    VerticalDivider(
                        modifier = Modifier
                            .width(Dimens.Size.Divider.asDP())
                            .fillMaxHeight()
                            .padding(vertical = Dimens.Padding.Medium.asDP())
                            .align(Alignment.CenterVertically),
                        thickness = Dimens.Size.Divider.asDP(),
                        color = bg_decorator_color,
                    )
                }
            }
        }
    }
}
