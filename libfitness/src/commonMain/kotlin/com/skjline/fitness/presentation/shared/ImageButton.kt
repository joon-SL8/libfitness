package com.skjline.fitness.presentation.shared

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import com.skjline.fitness.presentation.shared.style.Dimens

@Composable
fun ImageButton(
    modifier: Modifier = Modifier,
    bitmap: ImageBitmap,
    text: String,
    action: () -> Unit,
) {
    Button(
        modifier = modifier
            .wrapContentWidth()
            .height(Dimens.Size.Button.asDP())
            .padding(
                horizontal = Dimens.Padding.Medium.asDP()
            ),
        onClick = {
            action.invoke()
        },
    ) {
        Row(
            modifier = Modifier.align(Alignment.CenterVertically),
        ) {
            Image(
                modifier = Modifier.size(Dimens.Size.Icon.asDP()),
                alignment = Alignment.CenterStart,
                bitmap = bitmap,
                contentDescription = null,
            )
            Text(
                text = text,
                modifier = Modifier
                    .wrapContentSize()
                    .padding(
                        horizontal = Dimens.Padding.Medium.asDP()
                    ).align(Alignment.CenterVertically),
                maxLines = 1,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}