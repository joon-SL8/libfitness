package com.skjline.fitness.presentation.session.device.view

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.skjline.fitness.core.model.ble.BluetoothComponent.Companion.applyIconResource
import com.skjline.fitness.core.model.state.Operation
import com.skjline.fitness.core.model.state.OnDeviceUpdated
import com.skjline.fitness.feature.arrange.useCase.DeviceConnectionUseCase
import com.skjline.fitness.presentation.shared.style.font_error
import com.skjline.fitness.presentation.shared.style.font_light
import com.skjline.fitness.resources.Res
import com.skjline.fitness.resources.ble_device_not_available
import com.skjline.fitness.resources.ble_devices_list
import com.skjline.fitness.resources.ble_devices_list_searching
import org.jetbrains.compose.resources.stringResource

@Composable
fun DeviceListContainer(
    modifier: Modifier,
    state: Operation,
    selected: MutableSet<String>,
    scanConnect: DeviceConnectionUseCase,
) {

    when (state) {
        is OnDeviceUpdated -> {
            Text(
                modifier = modifier,
                text = stringResource(Res.string.ble_devices_list),
                style = MaterialTheme.typography.bodyLarge,
                color = font_light,
            )

            DeviceListView(
                list = state.devices.map {
                    it.component.applyIconResource()
                },
                onConnect = { dev, select ->
                    return@DeviceListView state.devices.let { list ->
                        list.firstOrNull { it.component.id == dev.id }
                    }?.let {
                        if (select) {
                            selected.add(it.component.id)
                        } else {
                            if (it.component.id in selected) {
                                selected.remove(it.component.id)
                            }
                        }
                        scanConnect(device = dev, select = select)
                    }
                },
            )
        }

        is Error -> {
            Text(
                modifier = modifier,
                text = stringResource(resource = Res.string.ble_device_not_available),
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.bodyLarge,
                color = font_error,
            )
        }

        else -> {
            Text(
                modifier = modifier,
                text = stringResource(resource = Res.string.ble_devices_list_searching),
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.bodyLarge,
                color = font_light,
            )
        }
    }
}