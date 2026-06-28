package com.skjline.fitness.feature.registration.presentation.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.skjline.fitness.core.utils.DispatcherProvider
import com.skjline.fitness.feature.registration.RegistrationViewModel.Companion.EXPIRE_COMPLETION_DISPLAY
import com.skjline.fitness.feature.registration.model.Action
import com.skjline.fitness.feature.registration.model.Finish
import com.skjline.fitness.feature.registration.model.Next
import com.skjline.fitness.feature.registration.model.ProcessComplete
import com.skjline.fitness.feature.registration.model.RegStep
import com.skjline.fitness.injection.AppComponent
import com.skjline.fitness.presentation.shared.style.Dimens
import com.skjline.fitness.presentation.shared.style.font_light
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.skjline.fitness.resources.Res
import com.skjline.fitness.resources.app_reg_complete
import org.jetbrains.compose.resources.stringResource
import org.koin.core.component.get

@Composable
fun Completion(
    modifier: Modifier = Modifier,
    handler: (regStep: RegStep, action: Action) -> Unit,
) {
    val dispatcher = AppComponent.get<DispatcherProvider>()
    LaunchedEffect(true) {
        CoroutineScope(dispatcher.io).launch {
            delay(EXPIRE_COMPLETION_DISPLAY)
            handler(Finish, Next(ProcessComplete(isReg = true, isSucceed = true)))
        }
    }

    Column(
        modifier = modifier.fillMaxSize().padding(Dimens.Padding.Large.asDP()),
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = stringResource(Res.string.app_reg_complete),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge,
            color = font_light,
        )
    }
}
