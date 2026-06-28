//package com.skjline.fitness.presentation.main.home.view
//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.border
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.RowScope
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material3.HorizontalDivider
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.composed
//import androidx.compose.ui.draw.drawBehind
//import androidx.compose.ui.geometry.Offset
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.platform.LocalDensity
//import androidx.compose.ui.unit.Dp
//import androidx.compose.ui.unit.sp
////import com.kizitonwose.calendar.core.CalendarDay
////import com.kizitonwose.calendar.core.DayPosition
//import com.skjline.fitness.presentation.shared.style.Dimens
//import com.skjline.fitness.presentation.shared.style.date_bg
//import com.skjline.fitness.presentation.shared.style.date_today_bottom_bevel
//
//@Composable
//fun DayHeader(dayString: String) {
//    Row(
//        modifier = Modifier
//            .padding(
//                vertical = Dimens.Padding.XSmall.asDP(),
//                horizontal = Dimens.Padding.Medium.asDP()
//            )
//            .height(Dimens.Size.Medium.asDP())
//    ) {
//        DayHeaderLine()
//        Text(
//            text = dayString,
//            modifier = Modifier.padding(
//                horizontal = Dimens.Padding.Medium.asDP()
//            ),
//            style = MaterialTheme.typography.labelSmall,
//            color = MaterialTheme.colorScheme.onSurfaceVariant
//        )
//        DayHeaderLine()
//    }
//}
//
//@Composable
//private fun RowScope.DayHeaderLine() {
//    HorizontalDivider(
//        modifier = Modifier
//            .weight(1f)
//            .align(Alignment.CenterVertically),
//        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
//    )
//}
//
//
//@Composable
//fun TrainingDay(day: CalendarDay) {
//    val dateInMonth = day.position == DayPosition.MonthDate
//    val mod = Modifier
//        .height(Dimens.Size.DayHeight.asDP())
//        .fillMaxWidth()
//    Column(modifier = mod) {
//        val isToday = false
////            day.date.atStartOfDay().dayOfMonth ==
////                    LocalDate.now().atStartOfDay().dayOfMonth
//
//        val fg = Color.DarkGray
//        val bg = date_bg
//        val border = Color.LightGray
//
//        val modifier = Modifier
//            .fillMaxWidth()
//            .height(Dimens.Size.Normal.asDP())
//            .background(bg)
//            .border(Dimens.Size.Border.asDP(), border)
//            .let {
//                if (isToday && dateInMonth) {
//                    it.bottomBorder(
//                        Dimens.Size.BottomBorder.asDP(),
//                        date_today_bottom_bevel
//                    )
//                } else {
//                    it
//                }
//            }
//
//        // daily date
//        Box(
//            modifier = modifier,
//            contentAlignment = Alignment.TopCenter,
//        ) {
//            if (dateInMonth) {
//                Text(
//                    modifier = Modifier
//                        .align(Alignment.CenterStart)
//                        .padding(start = Dimens.Padding.XSmall.asDP()),
//                    text = day.date.day.toString(),
//                    color = fg,
//                    fontSize = 12.sp,
//                )
//            }
//        }
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(Dimens.Size.XLarge.asDP())
//                .border(Dimens.Size.Border.asDP(), border),
//            contentAlignment = Alignment.Center,
//        ) {
//            // daily content box
//            if (dateInMonth) {
//                Column(modifier = Modifier.align(Alignment.Center)) {
//                    Text(
//                        text = "",
//                        color = Color.Black,
//                        fontSize = 16.sp,
//                    )
//                    Text(
//                        text = "",
//                        color = Color.Black,
//                        fontSize = 16.sp,
//                    )
//                }
//            }
//        }
//    }
//}
//
//fun Modifier.bottomBorder(strokeWidth: Dp, color: Color) = composed(
//    factory = {
//        val density = LocalDensity.current
//        val strokeWidthPx = density.run { strokeWidth.toPx() }
//
//        Modifier.drawBehind {
//            val width = size.width
//            val height = size.height - strokeWidthPx / 2
//
//            drawLine(
//                color = color,
//                start = Offset(x = 0f, y = height),
//                end = Offset(x = width, y = height),
//                strokeWidth = strokeWidthPx
//            )
//        }
//    }
//)
