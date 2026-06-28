package com.skjline.fitness.presentation.main.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.skjline.fitness.core.utils.DispatcherProvider
import com.skjline.fitness.data.storage.BearerStorage
import com.skjline.fitness.data.storage.StorageDatabase
import com.skjline.fitness.data.storage.Constants.Companion.PROFILE_KEY_FTP
import com.skjline.fitness.data.storage.Constants.Companion.PROFILE_KEY_MAX_HR
import com.skjline.fitness.feature.publish.strava.api.getStravaAuthorize
import com.skjline.fitness.injection.AppComponent
import com.skjline.fitness.presentation.shared.ImageButton
import com.skjline.fitness.presentation.shared.LyncProfileTextField
import com.skjline.fitness.resources.FTP
import com.skjline.fitness.resources.Res
import com.skjline.fitness.resources.connect_strava
import com.skjline.fitness.resources.ext_app
import com.skjline.fitness.resources.heart_rate
import com.skjline.fitness.resources.icon_strava_badge_48
import com.skjline.fitness.resources.profile_fragment_label
import com.skjline.fitness.resources.reconnect_strava
import com.skjline.fitness.resources.unit_bpm
import com.skjline.fitness.resources.unit_watt
import com.skjline.fitness.presentation.shared.style.Dimens
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.imageResource
import org.jetbrains.compose.resources.stringResource
import org.koin.core.component.get
import kotlin.random.Random

class ScreenProfile : Screen {

    override val key: ScreenKey
        get() = super.key + "${Random.nextDouble(Double.MIN_VALUE, Double.MAX_VALUE)}"

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        val dispatcherProvider = AppComponent.get<DispatcherProvider>()
        val bearerStorage = AppComponent.get<BearerStorage>()
        val db = AppComponent.get<StorageDatabase>()

        val profile = db.database.userProfileQueries.getAll().executeAsList()
        val ftp = profile.firstOrNull { it.name == PROFILE_KEY_FTP }?.data_ ?: "100"
        val hr = profile.firstOrNull { it.name == PROFILE_KEY_MAX_HR }?.data_ ?: "100"

        val hasExtAppToken = remember {
            bearerStorage.getToken()?.accessToken?.isNotEmpty() == true
        }

        Column(
            modifier = Modifier.fillMaxSize()
                .padding(
                    horizontal = Dimens.Padding.Medium.asDP(),
                    vertical = Dimens.Padding.XLarge.asDP()
                ),
            verticalArrangement = Arrangement.spacedBy(Dimens.Spacing.Normal.asDP()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = stringResource(Res.string.profile_fragment_label),
                modifier = Modifier.padding(
                    horizontal = Dimens.Padding.Medium.asDP()
                ).align(Alignment.Start),
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )

            LyncProfileTextField(
                modifier = Modifier.padding(start = Dimens.Padding.Medium.asDP()),
                label = stringResource(Res.string.FTP),
                unit = stringResource(Res.string.unit_watt),
                content = ftp,
            ) { value ->
                println("changed FTP content: $value")
            }

            LyncProfileTextField(
                modifier = Modifier.padding(start = Dimens.Padding.Medium.asDP()),
                label = stringResource(Res.string.heart_rate),
                unit = stringResource(Res.string.unit_bpm),
                content = hr,
            ) { value ->
                println("changed HR content: $value")
            }

            Text(
                text = stringResource(Res.string.ext_app),
                modifier = Modifier.padding(
                    horizontal = Dimens.Padding.Medium.asDP()
                ).align(Alignment.Start),
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )

            val res = if (hasExtAppToken) {
                Res.string.connect_strava
            } else {
                Res.string.reconnect_strava
            }
            stringResource(res)

            ImageButton(
                modifier = Modifier.align(Alignment.End)
                    .padding(horizontal = Dimens.Padding.Medium.asDP()),
                bitmap = imageResource(Res.drawable.icon_strava_badge_48),
                text = stringResource(Res.string.connect_strava),
            ) {
                CoroutineScope(dispatcherProvider.io).launch {
                    val deeplink = "Skjline%3A%2F%2F"
                    val authorization = getStravaAuthorize(deeplink)
                    authorization.authenticate()
                }
            }
        }
    }
}
