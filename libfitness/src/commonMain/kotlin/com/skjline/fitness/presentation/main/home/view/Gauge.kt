package com.skjline.fitness.presentation.main.home.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.skjline.fitness.presentation.shared.toHalfPieBitmap
import com.skjline.fitness.presentation.shared.style.Dimens
import com.skjline.fitness.presentation.shared.style.ftp_bg_color
import com.skjline.fitness.presentation.shared.style.points_bg_color
import com.skjline.fitness.presentation.shared.style.vo2max_bg_color
import com.skjline.fitness.resources.Res
import com.skjline.fitness.resources.ic_arrow_left
import com.skjline.fitness.resources.icon_skjline_logo
import org.jetbrains.compose.resources.imageResource
import org.jetbrains.compose.resources.vectorResource

@Composable
fun ClickableMessage(
    message: String,
    modifier: Modifier = Modifier.padding(
        Dimens.Padding.Normal.asDP()
    ),
    onClickHandler: () -> Unit = {},
) {
    Text(
        modifier = modifier,
        text = AnnotatedString(message),
//        onClickHandler = { onClickHandler.invoke() },
    )
}

@Composable
fun StatusView(
    label: String,
    message: String,
    onClickHandler: () -> Unit = {},
) {
    Box(
        modifier = Modifier.padding(
            Dimens.Padding.Small.asDP()
        ),
    ) {
        Column {
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = AnnotatedString(label),
                style = MaterialTheme.typography.labelMedium,
//                ClickableText(
//                maxLines = 1,
//                onClick = {},
            )
            Row(
                modifier = Modifier
                    .wrapContentWidth()
                    .align(Alignment.CenterHorizontally)
                    .padding(top = Dimens.Padding.Subtle.asDP())
            ) {
                Image(
                    imageVector = vectorResource(Res.drawable.ic_arrow_left),
                    modifier = Modifier.align(Alignment.CenterVertically),
                    contentDescription = null
                )
                Text(
                    text = message,
                    modifier = Modifier.align(Alignment.CenterVertically),
                    style = MaterialTheme.typography.headlineLarge,
                )
            }
        }
    }
}

@Composable
fun FTPGauge(
    ftp: String,
    modifier: Modifier = Modifier
        .width(IntrinsicSize.Min)
        .height(IntrinsicSize.Min),
    onClickHandler: () -> Unit = {},
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                ftp_bg_color,
                RoundedCornerShape(
                    Dimens.RoundedCorner.Large.asDP()
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(Dimens.Padding.Medium.asDP()),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "FTP",
                modifier = Modifier
                    .fillMaxHeight(0.10f)
                    .align(alignment = Alignment.Start),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Black,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
//            LineChart(
//                linesChartData = listOf(
//                    LineChartData(
//                        points = listOf(
//                            LineChartData.Point(
//                                2.1f,
//                                "week 1"
//                            ),
//                            LineChartData.Point(
//                                2.5f,
//                                "week 2"
//                            ),
//                            LineChartData.Point(
//                                2.8f,
//                                "week 3"
//                            )
//                        ),
//                        lineDrawer = SolidLineDrawer(),
//                    )
//                ),
//                // Optional properties.
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .fillMaxHeight(.55f),
//                animation = simpleChartAnimation(),
//                pointDrawer = FilledCircularPointDrawer(),
//                xAxisDrawer = SimpleXAxisDrawer(),
//                yAxisDrawer = SimpleYAxisDrawer(),
//                horizontalOffset = 5f,
//            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "${ftp}w",
                    modifier = Modifier
                        .padding(top = Dimens.Padding.XSmall.asDP())
                        .wrapContentWidth()
                        .align(Alignment.CenterVertically),
                    maxLines = 1,
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = FontWeight.SemiBold,
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "3.7 w/kg",
                    modifier = Modifier
                        .padding(start = Dimens.Padding.XSmall.asDP())
                        .wrapContentWidth()
                        .align(Alignment.CenterVertically),
                    maxLines = 1,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}


@Composable
fun VO2MaxGauge(
    vo2: String,
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight(),
    onClickHandler: () -> Unit = {},
) {
    Box(
        modifier = modifier
            .background(
                vo2max_bg_color,
                RoundedCornerShape(Dimens.RoundedCorner.Large.asDP())
            )
            .clickable {
                onClickHandler.invoke()
            },
        contentAlignment = Alignment.Center
    ) {
        Column(modifier = Modifier.padding(Dimens.Padding.Medium.asDP())) {
            Text(
                text = "VO2 Max",
                modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Black,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Box(modifier = Modifier.padding(top = Dimens.Padding.Medium.asDP())) {
                Image(
                    modifier = Modifier.align(Alignment.TopCenter),
                    bitmap = listOf(0.28f, 0.72f).toHalfPieBitmap(
                        colorlist = listOf(
                            Color.Red,
                            Color.DarkGray,
                            Color.Cyan
                        )
                    ),
                    contentScale = ContentScale.Inside,
                    contentDescription = ""
                )

                Column(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(top = Dimens.Padding.Large.asDP())
                ) {
                    Text(
                        text = vo2,
                        modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "Top 5%",
                        modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
fun LyncPointGaugeLarge(
    points: String,
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight(),
    onClickHandler: () -> Unit = {},
) {
    Box(
        modifier = modifier
            .background(
                points_bg_color,
                RoundedCornerShape(16.dp)
            )
            .clickable {
                onClickHandler.invoke()
            },
        contentAlignment = Alignment.Center,
    ) {
        Row(modifier = Modifier.padding(vertical = Dimens.Padding.Medium.asDP())) {
            Text(
                text = points,
                modifier = Modifier
                    .padding(start = Dimens.Padding.XSmall.asDP())
                    .align(Alignment.CenterVertically),
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Column(
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Image(
                    modifier = Modifier
                        .height(Dimens.Size.Small.asDP())
                        .padding(start = Dimens.Padding.XSmall.asDP()),
                    bitmap = imageResource(Res.drawable.icon_skjline_logo),
                    contentDescription = null
                )
                Text(
                    text = "points",
                    modifier = Modifier.padding(
                        top = Dimens.Padding.XSmall.asDP(),
                        start = Dimens.Padding.XSmall.asDP()
                    ),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
