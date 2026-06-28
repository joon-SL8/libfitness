package com.skjline.fitness.presentation.session.activity.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.skjline.fitness.core.model.generic.Const.Companion.EMPTY
import com.skjline.fitness.core.model.generic.PacketType
import com.skjline.fitness.feature.collect.service.DataCollectionService
import com.skjline.fitness.core.model.packet.DataPacket
import com.skjline.fitness.presentation.shared.style.Dimens
import com.skjline.fitness.resources.Res
import com.skjline.fitness.resources.cadence
import com.skjline.fitness.resources.heart_rate
import com.skjline.fitness.resources.interval
import com.skjline.fitness.resources.power
import com.skjline.fitness.resources.target_power
import com.skjline.fitness.resources.total_time
import org.jetbrains.compose.resources.stringResource

@Composable
fun TelemetryCollectionStaticView(
    modifier: Modifier,
    availableServices: List<PacketType>,
    collectionService: DataCollectionService,
) {
    val viewColumns = 2
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(count = viewColumns),
        verticalArrangement = Arrangement.spacedBy(Dimens.Padding.XSmall.asDP()),
        horizontalArrangement = Arrangement.spacedBy(Dimens.Padding.XSmall.asDP()),
        content = {
            val itemModifier = Modifier.fillMaxWidth()

            items(visibleServices.size) { index ->
                val service = visibleServices[index]

                val name = service.toStringResource()
                val provider = collectionService.getProviderOf(service)
                val predicate: (DataPacket) -> Boolean = { p -> p::class.simpleName == service.name }

                TelemetryComponentView(
                    modifier = itemModifier,
                    label = name,
                    type = service.toString(),
                    provider = provider?.observeDataPacket(),
                    predicate = predicate,
                )
            }
        }
    )
}

private val visibleServices = listOf(
    PacketType.TotalTime, PacketType.Interval,
    PacketType.Power, PacketType.TargetPower,
    PacketType.Cadence, PacketType.HRData,
)

@Composable
private fun PacketType.toStringResource(): String = when (this) {
    PacketType.Cadence -> stringResource(Res.string.cadence)
    PacketType.HRData -> stringResource(Res.string.heart_rate)
    PacketType.TotalTime -> stringResource(Res.string.total_time)
    PacketType.Power -> stringResource(Res.string.power)
    PacketType.TargetPower -> stringResource(Res.string.target_power)
    PacketType.Interval -> stringResource(Res.string.interval)
    else -> EMPTY
}
