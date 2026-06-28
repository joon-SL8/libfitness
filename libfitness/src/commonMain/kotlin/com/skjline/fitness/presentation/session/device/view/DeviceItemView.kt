package com.skjline.fitness.presentation.session.device.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.skjline.fitness.core.model.generic.Const.Companion.EMPTY
import com.skjline.fitness.core.model.ble.BluetoothComponent
import com.skjline.fitness.core.model.packet.DataPacket
import com.skjline.fitness.core.utils.DispatcherProvider
import com.skjline.fitness.core.utils.toDrawableRes
import com.skjline.fitness.injection.AppComponent
import com.skjline.fitness.presentation.shared.style.Blue40
import com.skjline.fitness.presentation.shared.style.Blue90
import com.skjline.fitness.presentation.shared.style.Dimens
import com.skjline.fitness.presentation.shared.style.background_light
import com.skjline.fitness.presentation.shared.style.font_dark
import com.skjline.fitness.presentation.shared.style.font_light
import com.skjline.fitness.resources.Res
import com.skjline.fitness.resources.ic_check_circle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.vectorResource
import org.koin.core.component.get

@Composable
fun DeviceItemView(
    device: BluetoothComponent?,
    onClickListener: (BluetoothComponent, Boolean) -> Flow<DataPacket>?,
) {

    var localDataListenerScope: CoroutineScope? = null
    val dispatcherProvider = AppComponent.get<DispatcherProvider>()

    val data = remember { mutableStateOf(EMPTY) }
    val selected = remember { mutableStateOf(false) }

    DisposableEffect(localDataListenerScope) {
        onDispose {
            localDataListenerScope?.cancel()
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                selected.value = !selected.value
                onClickListener(device!!, selected.value)?.let { flow ->
                    CoroutineScope(dispatcherProvider.io + Job()).let {
                        localDataListenerScope = it.apply {
                            launch {
                                flow.collectLatest { packet ->
                                    data.value = packet.formatter()
                                }
                            }
                        }
                    }
                } ?: run {
                    data.value = EMPTY
                    localDataListenerScope?.cancel()
                }
            },
        colors = CardColors(
            contentColor = font_light,
            containerColor = if (selected.value) Blue40 else Blue90,
            disabledContainerColor = font_dark,
            disabledContentColor = background_light,
        ),
    ) {
        val commonModifier = Modifier.padding(
            horizontal = Dimens.Padding.Medium.asDP()
        )
        Row(
            modifier = Modifier.padding(
                horizontal = Dimens.Padding.Normal.asDP(),
                vertical = Dimens.Padding.XSmall.asDP()
            )
        ) {
            device?.resource?.toDrawableRes()?.let { painterResource(it) }?.let { painter ->
                Image(
                    modifier = Modifier
                        .width(Dimens.Size.Large.asDP())
                        .height(Dimens.Size.Large.asDP())
                        .align(alignment = Alignment.CenterVertically),
                    painter = painter,
                    contentDescription = null,
                )
            }

            Column(
                modifier = Modifier.align(alignment = Alignment.CenterVertically),
                verticalArrangement = Arrangement.spacedBy(Dimens.Spacing.XSmall.asDP()),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        text = device?.name.orEmpty(),
                        modifier = commonModifier,
                        style = MaterialTheme.typography.bodyLarge,
                        color = font_light,
                    )
                    Text(
                        text = device?.category.orEmpty(),
                        modifier = commonModifier,
                        style = MaterialTheme.typography.bodyLarge,
                        color = font_light,
                    )
                    if (selected.value) {
                        Image(
                            modifier = Modifier
                                .padding(start = Dimens.Padding.Small.asDP())
                                .height(Dimens.Size.Normal.asDP())
                                .width(Dimens.Size.Normal.asDP())
                                .align(Alignment.CenterVertically),
                            imageVector = vectorResource(Res.drawable.ic_check_circle),
                            contentDescription = null,
                        )
                    }
                }
                Text(
                    text = device?.id ?: EMPTY,
                    modifier = commonModifier,
                    style = MaterialTheme.typography.bodyLarge,
                    color = font_light,
                )
                Text(
                    text = data.value,
                    modifier = commonModifier,
                    style = MaterialTheme.typography.bodyLarge,
                    color = font_light,
                )
            }
        }
    }
}
