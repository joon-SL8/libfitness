package com.skjline.fitness.presentation.main.plan.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.skjline.fitness.data.asset.model.AssetProperty
import com.skjline.fitness.presentation.shared.style.Dimens

@Composable
fun TrainingPlanListView(
    modifier: Modifier = Modifier.fillMaxSize(),
    content: List<AssetProperty>,
    onClickListener: (AssetProperty) -> Unit,
) {

    LazyColumn(
        modifier = modifier
            .padding(Dimens.Padding.Normal.asDP()),
        verticalArrangement = Arrangement.spacedBy(Dimens.Spacing.Small.asDP()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        item {
            Text(
                text = if (content.size > 1) {
                    "Let\'s find a training in below"
                } else {
                    "There isn't any training item."
                },
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.fillMaxWidth()
                    .padding(
                        horizontal = Dimens.Padding.Small.asDP(),
                        vertical = Dimens.Padding.Medium.asDP()
                    )
            )
        }

        content.forEach { path ->
            item {
                TrainingListItemView(path, onClickListener)
            }
        }
    }
}