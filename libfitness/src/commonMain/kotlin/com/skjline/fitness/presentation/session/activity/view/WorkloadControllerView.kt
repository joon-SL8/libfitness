package com.skjline.fitness.presentation.session.activity.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.skjline.fitness.feature.collect.service.DataCollectionService
import com.skjline.fitness.presentation.shared.style.Dimens
import com.skjline.fitness.resources.Res
import com.skjline.fitness.resources.adjust_workload
import com.skjline.fitness.resources.dec_10
import com.skjline.fitness.resources.inc_10
import org.jetbrains.compose.resources.stringResource

@Composable
fun WorkloadControllerView(
    modifier: Modifier,
    dataCollectionService: DataCollectionService,
) {
    var offset by remember { mutableStateOf(0) }
    Column {
        Text(
            modifier = Modifier.wrapContentSize().align(Alignment.CenterHorizontally),
            text = stringResource(resource = Res.string.adjust_workload),
            style = MaterialTheme.typography.bodyMedium,
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                modifier = modifier.wrapContentSize().padding(
                    horizontal = Dimens.Padding.XSmall.asDP(),
                    vertical = Dimens.Padding.Small.asDP()
                ),
                shape = MaterialTheme.shapes.large,
                content = {
                    Text(
                        stringResource(resource = Res.string.dec_10),
                        modifier = Modifier.padding(
                            vertical = Dimens.Padding.Small.asDP()
                        ),
                        style = MaterialTheme.typography.bodyLarge,
                    )
                },
                onClick = {
                    // decrease by 10 W
                    offset = dataCollectionService.updatePowerOffset(-10)
                }
            )

            Text(
                modifier = Modifier.fillMaxWidth(0.5f)
                    .align(Alignment.CenterVertically),
                text = "$offset",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.displaySmall,
            )

            Button(
                modifier = modifier.wrapContentSize().padding(
                    horizontal = Dimens.Padding.XSmall.asDP(),
                    vertical = Dimens.Padding.Small.asDP()
                ),
                shape = MaterialTheme.shapes.large,
                content = {
                    Text(
                        text = stringResource(resource = Res.string.inc_10),
                        modifier = Modifier.padding(
                            vertical = Dimens.Padding.Small.asDP()
                        ),
                        style = MaterialTheme.typography.bodyLarge,
                    )
                },
                onClick = {
                    // increase by 10 W
                    offset = dataCollectionService.updatePowerOffset(10)
                }
            )
        }
    }
}