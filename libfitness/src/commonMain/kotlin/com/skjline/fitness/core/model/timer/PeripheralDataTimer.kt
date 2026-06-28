package com.skjline.fitness.core.model.timer

import com.skjline.fitness.core.model.ble.data.GattCharacteristic
import com.skjline.fitness.core.model.generic.DataTimer
import com.skjline.fitness.core.model.packet.DataPacket
import com.skjline.fitness.core.utils.DispatcherProvider
import com.skjline.fitness.data.asset.model.AssetProperty
import com.skjline.fitness.injection.AppComponent
import org.koin.core.component.get

abstract class PeripheralDataTimer<P: DataPacket>(
    protected val asset: AssetProperty,
    protected val dispatcherProvider: DispatcherProvider = AppComponent.get<DispatcherProvider>(),
) : DataTimer<P> {
    abstract val characteristics: Array<GattCharacteristic>

    abstract fun getNextCharacter()
}
