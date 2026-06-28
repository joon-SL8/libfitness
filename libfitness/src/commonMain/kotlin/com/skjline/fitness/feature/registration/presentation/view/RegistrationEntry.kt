package com.skjline.fitness.feature.registration.presentation.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import com.skjline.fitness.core.model.generic.Const.Companion.EMPTY
import com.skjline.fitness.data.storage.StorageDatabase
import com.skjline.fitness.feature.registration.model.Action
import com.skjline.fitness.feature.registration.model.ApplyCredential
import com.skjline.fitness.feature.registration.model.CredentialInput
import com.skjline.fitness.feature.registration.model.Next
import com.skjline.fitness.feature.registration.model.Register
import com.skjline.fitness.feature.registration.model.RegStep
import com.skjline.fitness.injection.AppComponent
import com.skjline.fitness.presentation.shared.LyncProfileTextField
import com.skjline.fitness.presentation.shared.style.Dimens
import com.skjline.fitness.presentation.shared.style.font_dark
import com.skjline.fitness.resources.Res
import com.skjline.fitness.resources.app_greeting
import com.skjline.fitness.resources.app_sign_in
import com.skjline.fitness.resources.password
import com.skjline.fitness.resources.register
import com.skjline.fitness.resources.sign_in
import com.skjline.fitness.resources.username
import io.ktor.client.request.invoke
import org.jetbrains.compose.resources.stringResource
import org.koin.core.component.get

@Composable
fun RegistrationEntry(
    modifier: Modifier = Modifier,
    handler: (regStep: RegStep, action: Action) -> Unit,
) {

    val storageDatabase = AppComponent.get<StorageDatabase>()

    val isRegistered = remember { mutableStateOf(false) }

    val usernameEntry = remember { mutableStateOf(EMPTY) }
    val passwordEntry = remember { mutableStateOf(EMPTY) }

    val text = stringResource(Res.string.app_greeting)

    LaunchedEffect(true) {
        isRegistered.value = storageDatabase.database.userProfileQueries
            .getAll().executeAsList().any()
    }

    Column(
        modifier = Modifier.fillMaxWidth().padding(
            horizontal = Dimens.Padding.Normal.asDP(),
            vertical = Dimens.Padding.XLarge.asDP(),
        ),
        verticalArrangement = Arrangement.spacedBy(Dimens.Spacing.Normal.asDP())
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(
                horizontal = Dimens.Padding.Medium.asDP()
            ),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Text(
            text = stringResource(Res.string.app_sign_in),
            modifier = Modifier.padding(
                horizontal = Dimens.Padding.Medium.asDP()
            ),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        LyncProfileTextField(
            modifier = Modifier.fillMaxWidth(),
            label = stringResource(Res.string.username),
            unit = EMPTY,
            content = EMPTY,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Unspecified,
                autoCorrectEnabled = false,
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Unspecified,
                platformImeOptions = null,
                showKeyboardOnFocus = null,
                hintLocales = null
            ),
            changeListener = { username ->
                usernameEntry.value = username
            }
        )

        LyncProfileTextField(
            modifier = Modifier.fillMaxWidth(),
            label = stringResource(Res.string.password),
            unit = EMPTY,
            content = EMPTY,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Unspecified,
                autoCorrectEnabled = false,
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Unspecified,
                platformImeOptions = null,
                showKeyboardOnFocus = null,
                hintLocales = null
            ),
            changeListener = { password ->
                passwordEntry.value = password
            }
        )

        if (isRegistered.value) {
            Button(
                modifier = Modifier.fillMaxWidth()
                    .height(Dimens.Size.Button.asDP()),
                onClick = {
                    val username = usernameEntry.value
                    val password = passwordEntry.value
                    handler(ApplyCredential, Next(CredentialInput(username, password)))
                }
            ) {
                Text(
                    modifier = Modifier,
                    text = stringResource(Res.string.sign_in),
                    style = MaterialTheme.typography.bodyMedium,
                    color = font_dark,
                )
            }
        } else {
            Spacer(modifier = Modifier.weight(1.0f))

            Button(
                modifier = Modifier.fillMaxWidth().height(Dimens.Size.Button.asDP()),
                onClick = {
                    val username = usernameEntry.value
                    val password = passwordEntry.value
                    handler(ApplyCredential, Register(CredentialInput(username, password)))
                }
            ) {
                Text(
                    modifier = Modifier,
                    text = stringResource(Res.string.register),
                    style = MaterialTheme.typography.bodyMedium,
                    color = font_dark,
                )
            }
        }
    }
}
