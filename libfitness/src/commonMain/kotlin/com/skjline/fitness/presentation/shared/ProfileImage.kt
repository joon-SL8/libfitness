package com.skjline.fitness.presentation.shared

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.skjline.fitness.presentation.shared.style.Dimens
import com.skjline.fitness.resources.Res
import com.skjline.fitness.resources.ic_account_profile
import org.jetbrains.compose.resources.painterResource

@Composable
fun ProfileImage() {
    Image(
        modifier = Modifier
            .width(Dimens.Size.XLarge.asDP())
            .height(Dimens.Size.XLarge.asDP()),
        painter = painterResource(Res.drawable.ic_account_profile),
        contentDescription = null,
    )
}
