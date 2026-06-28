package com.skjline.fitness.presentation.session.activity.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.skjline.fitness.core.model.packet.Cadence
import com.skjline.fitness.core.model.packet.DataPacket
import com.skjline.fitness.core.model.packet.Initial
import com.skjline.fitness.core.utils.DispatcherProvider
import com.skjline.fitness.injection.AppComponent
import com.skjline.fitness.presentation.shared.style.Dimens
import com.skjline.fitness.presentation.shared.style.Grey20
import com.skjline.fitness.presentation.shared.style.Grey90
import com.skjline.fitness.presentation.shared.style.font_light
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import org.koin.core.component.get

@Composable
fun <P : DataPacket> TelemetryComponentView(
    modifier: Modifier = Modifier.padding(vertical = 0.dp),
    label: String,
    type: String,
    provider: Flow<P>?,
    predicate: (P) -> Boolean = { true }
) {
    val dispatcherProvider: DispatcherProvider = AppComponent.get<DispatcherProvider>()

    val scope = CoroutineScope(dispatcherProvider.io + Job())
    var content by remember { mutableStateOf<DataPacket>(Initial) }

    DisposableEffect(label) {
        scope.launch {
            provider?.filter(predicate)?.collectLatest { data ->
                if (type == data::class.simpleName) {
                    if (type == Cadence::class.simpleName && data.data.getValueAsNumber() == 0 && content.data.getValueAsNumber() != 0) {
                        // skip
                        println("skip zero data(${data::class.simpleName})")
                    } else {
                        content = data
                        val formatted = data.formatter()
                        if (formatted == "---") {
                            println("unformatted data(${data::class.simpleName})")
                        }
                    }
                }
            }
        }

        onDispose {
            scope.cancel()
        }
    }

    Column(
        modifier = modifier
            .wrapContentHeight()
            .background(
                color = Grey90,
                shape = RoundedCornerShape(
                    Dimens.RoundedCorner.Large.asDP()
                )
            )
            .border(
                width = Dimens.Size.Border.asDP(),
                color = Grey20,
                shape = RoundedCornerShape(
                    Dimens.RoundedCorner.Large.asDP()
                ),
            ),
    ) {
        Text(
            modifier = Modifier
                .padding(
                    start = Dimens.Padding.Normal.asDP(),
                    top = Dimens.Padding.Subtle.asDP(),
                )
                .align(Alignment.Start),
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )

        Text(
            modifier = Modifier
                .padding(
                    start = Dimens.Padding.XSmall.asDP(),
                    end = Dimens.Padding.XSmall.asDP(),
                    top = Dimens.Padding.Subtle.asDP(),
                    bottom = Dimens.Padding.Subtle.asDP(),
                )
                .align(Alignment.End),
            text = content.formatter(),
            color = font_light,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Normal
        )
    }
}