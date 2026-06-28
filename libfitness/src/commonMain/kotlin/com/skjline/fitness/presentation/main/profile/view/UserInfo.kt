package com.skjline.fitness.presentation.main.profile.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.skjline.fitness.presentation.shared.LyncProfileTextField
import com.skjline.fitness.presentation.shared.ProfileImage
import com.skjline.fitness.presentation.shared.style.Dimens
import com.skjline.fitness.resources.FTP
import com.skjline.fitness.resources.Res
import com.skjline.fitness.resources.unit_watt
import org.jetbrains.compose.resources.stringResource

@Composable
fun UserInfo(
    data: String,
    onContentChanged: (String) -> Unit = {},
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(Dimens.Spacing.Normal.asDP()),
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
        ) {
            Text(
                text = "My Name",
                modifier = Modifier
                    .align(alignment = Alignment.CenterVertically)
                    .padding(end = Dimens.Padding.XSmall.asDP()),
                textAlign = TextAlign.Start
            )
            ProfileImage()
        }

        val label = stringResource(Res.string.FTP)
        val unit = stringResource(Res.string.unit_watt)

        LyncProfileTextField(
            label = label,
            content = data,
            unit = unit,
        ) { onContentChanged(it) }
    }
}
