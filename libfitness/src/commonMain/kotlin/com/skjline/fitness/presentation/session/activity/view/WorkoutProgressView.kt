package com.skjline.fitness.presentation.session.activity.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import com.skjline.fitness.core.model.generic.DataTimer
import com.skjline.fitness.core.model.packet.DataPacket.Companion.MIN_TO_MILLIS
import com.skjline.fitness.core.model.packet.TotalTime
import com.skjline.fitness.core.model.packet.Initial
import com.skjline.fitness.core.model.packet.TotalTimeContent
import com.skjline.fitness.presentation.shared.style.Dimens
import com.skjline.fitness.presentation.shared.style.bg_train
import com.skjline.fitness.presentation.shared.style.user_activity_timer

@Composable
fun WorkoutProgressView(
    modifier: Modifier,
    content: ImageBitmap,
    totalTimeInSec: Float = 0f,
    hr: List<Int> = emptyList(),  // hr histogram graph values, a placeholder for future
    power: List<Int> = emptyList(),  // power histogram graph values, a placeholder for future
    provider: DataTimer<TotalTime>? = null,  // elapsed time provider
) {

    var offset by remember { mutableFloatStateOf(0f) }

    provider?.observeDataPacket()?.collectAsState(initial = Initial)?.let { packet ->
        if (totalTimeInSec > 0f) {
            val elapsed = (packet.value.data as TotalTimeContent).content
            offset = elapsed.toFloat() / (totalTimeInSec * MIN_TO_MILLIS)
        }
    }

    Image(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(0.35f)
            .background(bg_train, RoundedCornerShape(Dimens.RoundedCorner.Normal.asDP()))
            .graphicsLayer { compositingStrategy = CompositingStrategy.Offscreen }
            .drawWithCache {
                onDrawWithContent {
                    if (totalTimeInSec > 0f) {
                        val xOffset: Float = offset * this.size.width
                        this@onDrawWithContent.drawContent()

                        // draw timer bar
                        drawLine(
                            SolidColor(value = user_activity_timer),
                            Offset(xOffset, 0f),
                            Offset(xOffset, this.size.height),
                            strokeWidth = Stroke.DefaultMiter,
                        )
                    }
                }
            },
        bitmap = content,
        contentScale = ContentScale.FillBounds,
        contentDescription = null
    )
}
