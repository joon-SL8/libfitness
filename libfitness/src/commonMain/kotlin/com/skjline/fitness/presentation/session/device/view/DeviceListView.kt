package com.skjline.fitness.presentation.session.device.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.skjline.fitness.core.model.ble.BluetoothComponent
import com.skjline.fitness.core.model.packet.DataPacket
import com.skjline.fitness.presentation.shared.style.Dimens
import kotlinx.coroutines.flow.Flow

@Composable
fun DeviceListView(
    list: List<BluetoothComponent> = emptyList(),
    onConnect: (BluetoothComponent, Boolean) -> Flow<DataPacket>?,
) {
    LazyColumn(
        modifier = Modifier.padding(
            start = Dimens.Padding.Normal.asDP(),
            end = Dimens.Padding.Normal.asDP(),
            bottom = Dimens.Padding.Normal.asDP(),
        ),
        verticalArrangement = Arrangement.spacedBy(Dimens.Spacing.Small.asDP()),
    ) {
        items(
            count = list.size,
            key = { list[it].address }
        ) { index ->
            val device = list[index]

            DeviceItemView(
                device = device
            ) { dev, select ->
                onConnect(dev, select)
            }
        }
    }
}
