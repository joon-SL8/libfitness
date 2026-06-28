package com.skjline.fitness.presentation.session.device

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.skjline.fitness.core.model.generic.Const.Companion.EMPTY
import com.skjline.fitness.core.utils.parseFilenameFromPath
import com.skjline.fitness.core.model.state.OnDeviceUpdated
import com.skjline.fitness.feature.arrange.useCase.DeviceConnectionUseCase
import com.skjline.fitness.feature.arrange.useCase.ScanningDisposeUseCase
import com.skjline.fitness.feature.arrange.useCase.ScanningUseCase
import com.skjline.fitness.presentation.session.activity.SessionActivityScreen
import com.skjline.fitness.presentation.session.device.view.DeviceListContainer
import com.skjline.fitness.presentation.shared.ScreenTopBar
import com.skjline.fitness.presentation.shared.style.Dimens
import com.skjline.fitness.presentation.shared.style.bg_train
import com.skjline.fitness.presentation.shared.style.font_light
import com.skjline.fitness.resources.Res
import com.skjline.fitness.resources.ble_devices_header
import com.skjline.fitness.resources.ble_devices_session
import com.skjline.fitness.resources.start
import org.jetbrains.compose.resources.stringResource
import kotlin.random.Random

class DeviceSelectionScreen(
    private val path: String? = null,
    private val scanStarter: ScanningUseCase = ScanningUseCase(),
    private val scanDisposer: ScanningDisposeUseCase = ScanningDisposeUseCase(),
    private val scanConnect: DeviceConnectionUseCase = DeviceConnectionUseCase(),
) : Screen {

    override val key: ScreenKey
        get() = super.key + "${Random.nextDouble(Double.MIN_VALUE, Double.MAX_VALUE)}"

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val modifier: Modifier = Modifier.padding(Dimens.Padding.Normal.asDP())

        val selected = mutableSetOf<String>()
        val state = scanStarter.searchDeviceState.collectAsState()

        val selectedSession = if (path?.isNotEmpty() == true) {
            "${stringResource(Res.string.ble_devices_session)}:\n$path"
        } else {
            EMPTY
        }

        DisposableEffect(true) {
            scanStarter()
            onDispose {
                scanDisposer()
            }
        }

        Column(modifier = Modifier.fillMaxSize()) {
            ScreenTopBar(
                label = stringResource(Res.string.ble_devices_header),
            ) {
                if (navigator.canPop) {
                    navigator.pop()
                } else {
                    // close
                    println("need to close")
                }
            }

            if (selectedSession.isNotEmpty()) {
                val displayName = selectedSession.parseFilenameFromPath()
                Text(
                    text = displayName,
                    modifier = modifier,
                    style = MaterialTheme.typography.headlineSmall,
                    color = font_light,
                )
            }

            DeviceListContainer(
                modifier = modifier,
                state = state.value,
                selected = selected,
                scanConnect = scanConnect,
            )

            Button(
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(
                        end = Dimens.Padding.Normal.asDP(),
                        top = Dimens.Padding.Large.asDP()
                    ),
                shape = MaterialTheme.shapes.large,
                colors = if (selected.isEmpty()) {
                    ButtonDefaults.buttonColors(containerColor = bg_train)
                } else {
                    ButtonDefaults.buttonColors()
                },
                content = {
                    Text(
                        text = stringResource(resource = Res.string.start),
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.labelMedium,
                    )
                },
                enabled = state.value is OnDeviceUpdated,
                onClick = {
                    navigator.push(
                        SessionActivityScreen(
                            path.orEmpty(),
                            selected.toList(),
                        )
                    )
                }
            )
        }
    }
}
