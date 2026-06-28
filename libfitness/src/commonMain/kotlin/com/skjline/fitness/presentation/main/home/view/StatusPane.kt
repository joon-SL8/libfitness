package com.skjline.fitness.presentation.main.home.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.skjline.fitness.presentation.shared.style.Dimens
import com.skjline.fitness.presentation.shared.style.bg_decorator_color
import com.skjline.fitness.presentation.shared.style.bg_status_pane
import com.skjline.fitness.resources.Res
import com.skjline.fitness.resources.fitness_header
import org.jetbrains.compose.resources.stringResource

@Composable
fun StatusPane(
    onClickHandler: (viewId: Int) -> Unit = {}
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .padding(Dimens.Padding.Normal.asDP()),
            verticalArrangement = Arrangement.spacedBy(
                space = Dimens.Padding.Medium.asDP(),
                alignment = Alignment.CenterVertically
            ),
        ) {
            Text(
                text = stringResource(Res.string.fitness_header),
                style = MaterialTheme.typography.titleMedium,
                fontSize = 16.sp,
                modifier = Modifier.padding(top = Dimens.Padding.Normal.asDP()),
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min)
                    .padding(vertical = Dimens.Padding.XSmall.asDP())
                    .background(
                        bg_status_pane,
                        RoundedCornerShape(Dimens.RoundedCorner.MegaLarge.asDP())
                    ),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                val map = mapOf("Fatigue" to "80", "Fitness" to "200", "Form" to "70")
                map.onEachIndexed { index, message ->
                    StatusView(
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
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min),
            ) {
                FTPGauge(
                    ftp = "258",
                    modifier = Modifier
                        .fillMaxWidth(.6f)
                        .fillMaxHeight()
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth(.4f)
                        .align(Alignment.CenterEnd)
                ) {

                    VO2MaxGauge(
                        vo2 = "56", modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(
                                start = Dimens.Padding.XSmall.asDP(),
                                bottom = Dimens.Padding.Subtle.asDP()
                            )
                    ) {
                        onClickHandler.invoke(2)
                    }
                    LyncPointGaugeLarge(
                        points = "100", modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(
                                start = Dimens.Padding.XSmall.asDP(),
                                top = Dimens.Padding.Subtle.asDP()
                            )
                    ) {
                        onClickHandler.invoke(1)
                    }
                }
            }
        }
    }
}