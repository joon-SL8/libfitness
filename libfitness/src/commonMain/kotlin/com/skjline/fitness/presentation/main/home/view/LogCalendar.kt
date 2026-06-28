//package com.skjline.fitness.presentation.main.home.view
//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.PaddingValues
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.layout.wrapContentHeight
//import androidx.compose.material3.HorizontalDivider
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.geometry.Size
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.layout.onGloballyPositioned
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.unit.sp
//import androidx.compose.ui.unit.toSize
////import com.kizitonwose.calendar.compose.HorizontalCalendar
////import com.kizitonwose.calendar.compose.rememberCalendarState
////import com.kizitonwose.calendar.core.CalendarMonth
////import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
////import com.kizitonwose.calendar.core.now
////import com.kizitonwose.calendar.core.plusMonths
//import com.skjline.fitness.presentation.shared.style.Dimens
//import com.skjline.fitness.presentation.shared.style.background_dark
//import com.skjline.fitness.presentation.shared.style.font_dark
//import kotlinx.datetime.YearMonth
//import kotlin.time.ExperimentalTime
//
//@OptIn(ExperimentalTime::class)
//@Composable
//fun LogCalendar() {
//    val currentMonth = remember { YearMonth.now() }
//    val startMonth = remember { currentMonth }
//    val endMonth = remember { currentMonth.plusMonths(500) }
//
//    var parentSize by remember { mutableStateOf(Size.Zero)}
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(
//                Color.White
//            ).onGloballyPositioned { coordinates ->
//                parentSize = coordinates.parentLayoutCoordinates?.size?.toSize()?: Size.Zero
//            },
//    ) {
//        val state = rememberCalendarState(
//            startMonth = startMonth,
//            endMonth = endMonth,
//            firstVisibleMonth = currentMonth,
//            firstDayOfWeek = firstDayOfWeekFromLocale(),
//        )
//        HorizontalCalendar(
//            modifier = Modifier.fillMaxWidth(),
//            state = state,
////            dayContent = { day ->
////                TrainingDay(day)
////            },
//            monthHeader = { month ->
//                MonthHeader(month)
//            },
//            calendarScrollPaged = false,
//            contentPadding = PaddingValues(
//                horizontal = Dimens.Padding.Normal.asDP(),
//                vertical = Dimens.Padding.XSmall.asDP()),
//            monthContainer = { _, container ->
//                val screenWidth = Dimens.Size.Calendar.asDP()
//                Box(
//                    modifier = Modifier
//                        .width(screenWidth * .96f)
//                        .padding(Dimens.Padding.XSmall.asDP())) {
//                    container()
//                }
//            },
//            monthBody = { _, content ->
//                Box { content() }
//            },
//        )
//    }
//}
//
//@Composable
//private fun MonthHeader(calendarMonth: CalendarMonth) {
//    val daysOfWeek = calendarMonth.weekDays.first().map { it.date.dayOfWeek }
//    Column(
//        modifier = Modifier
//            .fillMaxWidth()
//            .wrapContentHeight(),
//    ) {
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(
//                    start = Dimens.Padding.Normal.asDP(),
//                    end = Dimens.Padding.Normal.asDP(),
//                    top = Dimens.Padding.Large.asDP(),
//                    bottom = Dimens.Padding.XSmall.asDP()
//                ),
//        ) {
//            Text(
//                text = "My Training",
//                style = MaterialTheme.typography.titleMedium,
//                textAlign = TextAlign.Start,
//            )
//            Spacer(modifier = Modifier.weight(1f))
//            Text(
//                text = calendarMonth.yearMonth.month.toString(),
//                style = MaterialTheme.typography.titleMedium,
//                textAlign = TextAlign.End,
//            )
//        }
//        Row(
//            modifier = Modifier
//                .background(color = background_dark)
//                .padding(vertical = Dimens.Padding.Subtle.asDP()),
//            ) {
//            for (dayOfWeek in daysOfWeek) {
//                val shortenDay = dayOfWeek.name
//                val day = if (shortenDay.length >= 2) {
//                    shortenDay[0].uppercase() + shortenDay[1].lowercase()
//                } else {
//                    ""
//                }
//
//                Text(
//                    text = day,
//                    modifier = Modifier.weight(1f),
//                    textAlign = TextAlign.Center,
//                    fontSize = 16.sp,
//                    color = font_dark,
//                    fontWeight = FontWeight.Bold,
//                )
//            }
//        }
//        HorizontalDivider(color = Color.Black)
//    }
//}
//
