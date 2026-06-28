/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.skjline.fitness.presentation.shared.style

import androidx.compose.ui.graphics.Color

interface PowerZoneColor {
    companion object {
        const val ZONE7_light = 0xFF974B94UL
        const val ZONE6B_light = 0xffd1001cUL
        const val ZONE6_light = 0xffd3212dUL
        const val ZONE5_light = 0xFF974B94UL
        const val ZONE4_light = 0xffffbf00UL
        const val ZONE3_light = 0xff0e5c00UL
        const val ZONE2B_light = 0xff007fffUL
        const val ZONE2_light = 0xff318ce7UL
        const val ZONE1_light = 0xff7e7e7eUL

        const val ZONE7: ULong = 0xff9647ffUL
        const val ZONE6B: ULong = 0xffff2e00UL
        const val ZONE6: ULong = 0xffff2e00UL
        const val ZONE5: ULong = 0xffff974bUL
        const val ZONE4: ULong = 0xffffd300UL
        const val ZONE3: ULong = 0xff5edc5bUL
        const val ZONE2B: ULong = 0xff007fffUL
        const val ZONE2: ULong = 0xff17A1FAUL
        const val ZONE1: ULong = 0xff757575UL

    }
}

val Blue10 = Color(0xFF000F5E)
val Blue20 = Color(0xFF001E92)
val Blue30 = Color(0xFF002ECC)
val Blue40 = Color(0xFF1546F6)
val Blue80 = Color(0xFFB8C3FF)
val Blue90 = Color(0xFFDDE1FF)

val DarkBlue10 = Color(0xFF00036B)
val DarkBlue20 = Color(0xFF000BA6)
val DarkBlue30 = Color(0xFF1026D3)
val DarkBlue40 = Color(0xFF3648EA)
val DarkBlue80 = Color(0xFFBBC2FF)
val DarkBlue90 = Color(0xFFDEE0FF)

val Yellow10 = Color(0xFF261900)
val Yellow20 = Color(0xFF402D00)
val Yellow30 = Color(0xFF5C4200)
val Yellow40 = Color(0xFF7A5900)
val Yellow80 = Color(0xFFFABD1B)
val Yellow90 = Color(0xFFFFDE9C)

val Red10 = Color(0xFF410001)
val Red20 = Color(0xFF680003)
val Red30 = Color(0xFF930006)
val Red40 = Color(0xFFBA1B1B)
val Red80 = Color(0xFFFFB4A9)
val Red90 = Color(0xFFFFDAD4)

val Orange10 = Color(0xFFFFE0B2)
val Orange20 = Color(0xFFFFCC80)
val Orange30 = Color(0xFFFFB74D)
val Orange40 = Color(0xFFFFA726)
val Orange50 = Color(0xFFFF9800)
val Orange60 = Color(0xFFFB8C00)
val Orange70 = Color(0xFFF57C00)
val Orange80 = Color(0xFFEF6C00)
val Orange90 = Color(0xFFE65100)

val Grey10 = Color(0xFF191C1D)
val Grey20 = Color(0xFF2D3132)
val Grey80 = Color(0xFFC4C7C7)
val Grey90 = Color(0xFFE0E3E3)
val Grey95 = Color(0xFFEFF1F1)
val Grey99 = Color(0xFFFBFDFD)

val BlueGrey30 = Color(0xFF45464F)
val BlueGrey50 = Color(0xFF767680)
val BlueGrey60 = Color(0xFF90909A)
val BlueGrey80 = Color(0xFFC6C5D0)
val BlueGrey90 = Color(0xFFE2E1EC)


val accent = Color(0xFFE1FD1F)
val bg_train = Color(0xFFD9D9D9)

val background_dark = Color(0xff282828)
val background_light = Color(0xffececec)

val font_light = Color(0xff323232)
val font_dark = Color(0xffe7e7e7)
val font_error = Red30
val date_bg = Color(0x80D9D9D9)
val date_today_bottom_bevel = Color(0x0629E7)

val zone_0 = Color(0xffe7e7e7)

val zone_7 = Color(PowerZoneColor.ZONE7)
val zone_6b = Color(PowerZoneColor.ZONE6B)
val zone_6a = Color(PowerZoneColor.ZONE6)
val zone_5 = Color(PowerZoneColor.ZONE5)
val zone_4 = Color(PowerZoneColor.ZONE4)
val zone_3 = Color(PowerZoneColor.ZONE3)
val zone_2b = Color(PowerZoneColor.ZONE2B)
val zone_2a = Color(PowerZoneColor.ZONE2)
val zone_1 = Color(PowerZoneColor.ZONE1)

val example_6_month_bg_color = Color(0xb2ebf2)
val example_6_month_bg_color2 = Color(0xF2C4B2)
val example_6_month_bg_color3 = Color(0xB2B8F2)

val ftp_bg_color = Color(0x99DCD4FF)
val vo2max_bg_color = Color(0x540629E7)
val points_bg_color = Color(0xFFD3ED1D)

val bg_status_pane = Color(0xF6F6F6)
val bg_decorator_color = Color(0xBDBDBD)
val status_trend_up = Color(0x520EB50B)
val status_trend_dn = Color(0x66FF2E00)

val user_activity_timer = Yellow80
val user_ftp = Orange70
val user_activity_hr = Red80
val user_activity_power = Blue80
