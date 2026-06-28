package com.skjline.fitness.core.model.timer

import com.skjline.fitness.core.model.generic.DataTimer
import com.skjline.fitness.core.model.packet.DataPacket
import com.skjline.fitness.core.utils.DispatcherProvider
import com.skjline.fitness.injection.AppComponent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.core.component.inject

abstract class ObservableDataTimer<P: DataPacket>: DataTimer<P> {
    protected val dispatcherProvider: DispatcherProvider by AppComponent.inject()

    protected abstract val observer: MutableStateFlow<P>
    override fun observeDataPacket() = observer.asStateFlow()
}
