package com.skjline.fitness.presentation.session.activity


//class ActivitySessionViewModel(
//    val course: MrcCourse,
//    private val bleComm: BluetoothComm,
//    private val intervalTimeDevice: IntervalTimeDevice,
//    private val sessionTargetPowerProvider: IntervalTargetPowerTimer,
//) : ViewModel() {
//    private val dispatcherProvider = DispatcherProvider
//    private val isTiramisu = Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU
//
//    private val commStatus = MutableStateFlow<BLEStatus>(BLEStatus.Init)
//    val status = commStatus.asStateFlow()
//
//    private val activitySessionManager = SessionManager(
//        viewModel = this,
//        dispatcher = dispatcherProvider,
//    )
//
//    private var lastPlot = CyclingPlot.build()
//
//    fun initialize() {
//        if (!isTiramisu) {
//            return
//        }
//
//        course.let {
//            activitySessionManager.initialize(it)
//        }
//
//        activitySessionManager.addProvider(intervalTimeDevice)
//        activitySessionManager.addProvider(sessionTargetPowerProvider)
//    }
//
//    fun getDataContentProvider(): SessionManager = activitySessionManager
//
//    fun sendPowerStateMessage(bool: Boolean) {
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) return
//
//        activitySessionManager.getProviderOf(TargetPower::class.java)?.let {
//            bleComm.getSelectedDeviceOperators().firstOrNull {
//                it.service.uuid == GattService.Fitness_Machine.uuid
//            }?.let { op ->
//                val s = op.features.firstOrNull {
//                    it.characteristics.firstOrNull { c ->
//                        c.characteristic.uuid.toString() == GattCharacteristic.Fitness_Machine_Control_Point.uuid
//                    } != null
//                }
//                s?.service?.characteristics?.firstOrNull {
//                    it.uuid.toString() == GattCharacteristic.Fitness_Machine_Control_Point.uuid
//                }?.let { ch ->
//                    val msg = if (bool) {
//                        // 0x07
//                        byteArrayOf(0x07)
//                    } else {
//                        // 0x08 - 0x01 (Stop) 0x02 (Pause)
//                        byteArrayOf(0x08, 0x01)
//                    }
//                    op.writeData(ch, msg)
//                }
//            }
//        }
//    }
//
//    fun sendPowerMessage(message: String) {
//        sendPowerMessage(message.toLong())
//    }
//
//    fun sendPowerMessage(message: Long) {
//        val msg = try {
//            with(message) {
//                byteArrayOf(0x5, (this and 0xff).toByte(), (this.shr(8) and 0xff).toByte())
//            }
//        } catch (ex: NumberFormatException) {
//            Timber.tag("sendPowerMessage").wtf("incorrect power: $message")
//            return
//        }
//
//        if (msg.isNotEmpty() && Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//            sendMessage(
//                uuid = GattCharacteristic.Fitness_Machine_Control_Point.uuid,
//                message = msg
//            )
//        }
//    }
//
//    @OptIn(ExperimentalStdlibApi::class)
//    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
//    private fun sendMessage(uuid: String, message: ByteArray) {
//        val name = uuid.getCharacteristicsOf()?.name ?: "Unknown"
//        Timber.tag("sendPowerMessage").wtf("$name: ${message.toHexString()}")
//
//        activitySessionManager.getProviderOf(TargetPower::class.java)?.let {
//            bleComm.getSelectedDeviceOperators().firstOrNull {
//                it.service.uuid == GattService.Fitness_Machine.uuid
//            }?.let { op ->
//                val s = op.features.firstOrNull {
//                    it.characteristics.firstOrNull { c ->
//                        c.characteristic.uuid.toString() == uuid
//                    } != null
//                }
//                s?.service?.characteristics?.firstOrNull {
//                    it.uuid.toString() == uuid
//                }?.let { ch ->
//                    op.writeData(ch, message)
//                }
//            }
//        }
//    }
//
//    @SuppressLint("NewApi")
//    fun refreshSelectedDevices() {
//        bleComm.getSelectedDeviceOperators().mapNotNull { operator ->
//            operator.asSessionProvider()
//        }.forEach {
//            activitySessionManager.addProvider(it)
//        }
//    }
//
//    fun clearListeners() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//            bleComm.getSelectedDeviceOperators().forEach { operator ->
//                operator.disconnect()
//            }
//        }
//    }
//}