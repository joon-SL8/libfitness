package com.skjline.fitness.feature.registration.presentation.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import com.skjline.fitness.core.model.generic.Const.Companion.EMPTY
import com.skjline.fitness.core.utils.toIntDefault
import com.skjline.fitness.feature.registration.model.Action
import com.skjline.fitness.feature.registration.model.Exit
import com.skjline.fitness.feature.registration.model.ApplyCredential
import com.skjline.fitness.feature.registration.model.Next
import com.skjline.fitness.feature.registration.model.ProfileInput
import com.skjline.fitness.feature.registration.model.SetProfile
import com.skjline.fitness.feature.registration.model.RegStep
import com.skjline.fitness.presentation.shared.LyncProfileTextField
import com.skjline.fitness.presentation.shared.style.Dimens
import com.skjline.fitness.presentation.shared.style.font_dark
import com.skjline.fitness.presentation.shared.style.font_light
import com.skjline.fitness.resources.Age
import com.skjline.fitness.resources.Name
import com.skjline.fitness.resources.Res
import com.skjline.fitness.resources.cancel
import com.skjline.fitness.resources.next
import com.skjline.fitness.resources.set_profile
import com.skjline.fitness.resources.set_profile_title
import org.jetbrains.compose.resources.stringResource

@Composable
fun SetUserProfile(
    modifier: Modifier = Modifier,
    handler: (regStep: RegStep, action: Action) -> Unit,
) {

    val nameEntry = remember { mutableStateOf(EMPTY) }
    val ageEntry = remember { mutableStateOf(EMPTY) }

    Column(
        modifier = modifier.fillMaxWidth().padding(
            horizontal = Dimens.Padding.Normal.asDP(),
            vertical = Dimens.Padding.XLarge.asDP(),
        ),
        verticalArrangement = Arrangement.spacedBy(Dimens.Spacing.Normal.asDP())
    ) {

        Text(
            text = stringResource(Res.string.set_profile_title),
            modifier = Modifier.padding(
                horizontal = Dimens.Padding.Medium.asDP()
            ),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = stringResource(Res.string.set_profile),
            style = MaterialTheme.typography.bodyLarge,
            color = font_light,
        )

        LyncProfileTextField(
            modifier = modifier.fillMaxWidth(),
            label = stringResource(Res.string.Name),
            unit = EMPTY,
            content = EMPTY,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Unspecified,
                autoCorrectEnabled = false,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Unspecified,
                platformImeOptions = null,
                showKeyboardOnFocus = null,
                hintLocales = null
            ),
            changeListener = {
                nameEntry.value = it
            }
        )

        LyncProfileTextField(
            modifier = modifier.fillMaxWidth(),
            label = stringResource(Res.string.Age),
            unit = EMPTY,
            content = EMPTY,
        ) {
            ageEntry.value = it
        }

        Button(
            modifier = modifier.fillMaxWidth()
                .height(Dimens.Size.Button.asDP()),
            onClick = {
                val name = nameEntry.value
                val age = ageEntry.value.toIntDefault()
                handler(SetProfile, Next(ProfileInput(name, age)))
            }
        ) {
            Text(
                text = stringResource(Res.string.next),
                style = MaterialTheme.typography.bodyMedium,
                color = font_dark,
            )
        }

        Button(
            modifier = modifier.fillMaxWidth()
                .height(Dimens.Size.Button.asDP()),
            onClick = { handler(ApplyCredential, Exit) }
        ) {
            Text(
                text = stringResource(Res.string.cancel),
                style = MaterialTheme.typography.bodyMedium,
                color = font_dark,
            )
        }

    }
}
