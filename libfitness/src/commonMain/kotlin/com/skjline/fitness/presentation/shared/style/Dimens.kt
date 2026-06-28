package com.skjline.fitness.presentation.shared.style

import androidx.compose.ui.unit.dp

sealed class Dimens {
    abstract val value: Int

    sealed class Size(override val value: Int) : Dimens() {
        data object XSmall : Size(value = 8)
        data object Small : Size(value = 12)
        data object Medium : Size(value = 16)
        data object Normal : Size(value = 24)
        data object Large : Size(value = 48)
        data object XLarge : Size(value = 64)

        data object Border: Size(value = 1)
        data object Divider: Size(value = 2)
        data object BottomBorder: Size(value = 4)
        data object Icon: Size(value = 24)
        data object Header: Size(value = 48)
        data object Button: Size(value = 56)
        data object DayHeight: Size(value = 84)
        data object IllustrationHeight : Size(value = 160)
        data object Illustration2XHeight : Size(value = 320)
        data object Calendar: Size(value = 980)
    }

    sealed class Padding(override val value: Int) : Dimens() {
        data object Subtle : Padding(value = 4)
        data object XSmall : Padding(value = 8)
        data object Small : Padding(value = 12)
        data object Medium : Padding(value = 16)
        data object Normal : Padding(value = 24)
        data object Large : Padding(value = 48)
        data object XLarge : Padding(value = 64)
    }

    sealed class Spacing(override val value: Int) : Dimens() {
        data object XSmall : Spacing(value = 4)
        data object Small : Spacing(value = 8)
        data object Normal : Spacing(value = 12)
        data object Large : Spacing(value = 16)
    }

    sealed class RoundedCorner(override val value: Int) : Dimens() {
        data object Small : Size(value = 4)
        data object Normal : Size(value = 8)
        data object Large : Size(value = 16)
        data object XLarge : Size(value = 32)
        data object MegaLarge : Size(value = 64)
    }

    fun asDP() = value.dp
}