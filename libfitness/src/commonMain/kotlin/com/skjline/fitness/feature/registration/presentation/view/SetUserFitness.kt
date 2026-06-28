package com.skjline.fitness.feature.registration.presentation.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.skjline.fitness.core.model.generic.Const.Companion.EMPTY
import com.skjline.fitness.core.utils.toIntDefault
import com.skjline.fitness.feature.registration.model.Action
import com.skjline.fitness.feature.registration.model.Exit
import com.skjline.fitness.feature.registration.model.FitnessInput
import com.skjline.fitness.feature.registration.model.ApplyCredential
import com.skjline.fitness.feature.registration.model.Next
import com.skjline.fitness.feature.registration.model.SetFitness
import com.skjline.fitness.feature.registration.model.RegStep
import com.skjline.fitness.presentation.shared.LyncProfileTextField
import com.skjline.fitness.presentation.shared.style.Dimens
import com.skjline.fitness.presentation.shared.style.font_dark
import com.skjline.fitness.presentation.shared.style.font_light
import com.skjline.fitness.resources.FTP
import com.skjline.fitness.resources.Res
import com.skjline.fitness.resources.cancel
import com.skjline.fitness.resources.next
import com.skjline.fitness.resources.set_fitness_info
import com.skjline.fitness.resources.set_fitness_info_title
import com.skjline.fitness.resources.set_ftp
import com.skjline.fitness.resources.weight
import com.skjline.fitness.resources.whats_ftp
import org.jetbrains.compose.resources.stringResource

@Composable
fun SetUserFitness(
    modifier: Modifier = Modifier,
    handler: (regStep: RegStep, action: Action) -> Unit,
) {

    val ftpEntry = remember { mutableStateOf(EMPTY) }
    val weightEntry = remember { mutableStateOf(EMPTY) }

    val text = stringResource(Res.string.set_fitness_info_title)
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
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = stringResource(Res.string.set_fitness_info),
            style = MaterialTheme.typography.bodyLarge,
            color = font_light,
        )

        val message = stringResource(Res.string.whats_ftp) + ":\n" +
                stringResource(Res.string.set_ftp)
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = message,
            style = MaterialTheme.typography.labelSmall,
            color = font_light,
        )


        LyncProfileTextField(
            modifier = modifier.fillMaxWidth(),
            label = stringResource(Res.string.weight),
            unit = EMPTY,
            content = EMPTY,
        ) {
            weightEntry.value = it
        }

        LyncProfileTextField(
            modifier = modifier.fillMaxWidth(),
            label = stringResource(Res.string.FTP),
            unit = EMPTY,
            content = EMPTY,
        ) {
            ftpEntry.value = it
        }

        Button(
            modifier = Modifier.fillMaxWidth().height(Dimens.Size.Button.asDP()),
            onClick = {
                val ftp = ftpEntry.value.toIntDefault()
                val weight = weightEntry.value.toIntDefault()
                handler(SetFitness, Next(FitnessInput(ftp, weight)))
            }
        ) {
            Text(
                text = stringResource(Res.string.next),
                style = MaterialTheme.typography.bodyMedium,
                color = font_dark,
            )
        }

        Button(
            modifier = Modifier.fillMaxWidth().height(Dimens.Size.Button.asDP()),
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
