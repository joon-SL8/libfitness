package com.skjline.fitness.presentation.main.plan.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import com.skjline.fitness.data.asset.model.AssetProperty
import com.skjline.fitness.presentation.shared.style.BlueGrey90
import com.skjline.fitness.presentation.shared.style.Dimens
import com.skjline.fitness.presentation.shared.style.Grey20
import com.skjline.fitness.presentation.shared.style.font_light
import com.skjline.fitness.resources.Res
import com.skjline.fitness.resources.ic_folder
import org.jetbrains.compose.resources.vectorResource

@Composable
fun TrainingListItemView(
    property: AssetProperty,
    onClickListener: (AssetProperty) -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize()
            .border(
                width = Dimens.Size.Border.asDP(),
                color = Grey20,
                shape = RoundedCornerShape(Dimens.RoundedCorner.Normal.asDP())
            )
            .border(width = Dimens.Size.Border.asDP(), color = BlueGrey90)
            .clickable {
                onClickListener.invoke(property)
            },
    ) {
        if (property.type == AssetProperty.Type.File) {
            val (schedule, name) = property.parseNameFromFilename()
            val commonModifier = Modifier.padding(top = Dimens.Padding.Subtle.asDP())
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterStart)
            ) {
                Text(
                    text = "$name: $schedule",
                    maxLines = 1,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = commonModifier
                        .wrapContentHeight()
//                        .fillMaxHeight(.7f)
                        .padding(Dimens.Padding.Small.asDP()),
                    textAlign = TextAlign.Center
                )

//                viewModel.parseSessionPlot(context, property)?.let { plot ->
//                    TodayTrainingView(
//                        course = plot,
//                        fg = colorResource(id = R.color.accent),
//                        onClickListener = {
//                            viewModel.navigateTrainAssets(
//                                context,
//                                property.copy(type = Property.Type.Select)
//                            )
//                        }
//                    )
//                }
            }
        } else if (property.type == AssetProperty.Type.Dir) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Dimens.Size.Header.asDP()),
            ) {

                Image(
                    modifier = Modifier
                        .padding(start = Dimens.Padding.Small.asDP())
                        .height(Dimens.Size.Normal.asDP())
                        .width(Dimens.Size.Normal.asDP())
                        .align(Alignment.CenterVertically),
                    colorFilter = ColorFilter.tint(font_light),
                    imageVector = vectorResource(Res.drawable.ic_folder),
                    contentDescription = null,
                )

                Text(
                    text = property.data as String,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Dimens.Padding.Small.asDP())
                        .align(Alignment.CenterVertically)
                )
            }
        }
    }
}


fun AssetProperty.parseNameFromFilename(): Pair<String, String> =
    (data as String).split("/").last().let { chars ->
        val index = chars.indexOfFirst { it == '-' }
        return Pair(
            chars.substring(0, index),
            chars.substring(
                index + 1, chars.lastIndexOf('.')
            ).replace("_", " ")
        )
    }


@Composable
fun WorkoutDetailView(
    label: String,
    message: String,
) {
    Column(
        modifier = Modifier
            .padding(Dimens.Padding.Small.asDP()),
    ) {
        Text(
            text = AnnotatedString(label),
            modifier = Modifier.align(Alignment.CenterHorizontally),
            style = MaterialTheme.typography.labelMedium,
            maxLines = 1,
        )
        Text(
            text = message,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = Dimens.Padding.Subtle.asDP()),
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 1,
        )
    }
}
