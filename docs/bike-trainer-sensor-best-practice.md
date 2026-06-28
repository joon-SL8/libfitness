# Best Practice Guide: BikeTrainerSensor & Capabilities

This document provides a reference manual and integration roadmap for client applications to observe power, speed, and cadence data and to apply target power changes using `BikeTrainerSensor`.

---

## 1. Capability-Based Architecture Overview

Instead of tightly coupling components to concrete sensor classes (which violates the **Open-Closed Principle** and **Interface Segregation Principle**), the codebase utilizes capability interfaces:

| Interface | Method / Property | Description |
| :--- | :--- | :--- |
| `PowerObservable` | `val powerFlow: Flow<Power>` | Emits real-time power (watts) data. |
| `CadenceObservable` | `val cadenceFlow: Flow<Cadence>` | Emits real-time cadence (RPM) data. |
| `SpeedObservable` | `val speedFlow: Flow<Speed>` | Emits real-time speed (MPH) data. |
| `PowerControllable` | `suspend fun setTargetPower(watts: Int): Result<Unit>` | Safely updates target trainer resistance. |

---

## 2. Integration Guide

### A. Discovery and Connection
Discovery is managed by `BluetoothSearchService`. When a device is selected, connect via the search service to instantiate the sensor:

```kotlin
val searchService: BluetoothSearchService = AppComponent.get()

// Start scanning for trainers
searchService.startScanning()

// Observe scanning state in UI
searchService.scanDeviceState.collect { state ->
    if (state is OnDeviceUpdated) {
        val discoveredDevices = state.devices
        // Update selection UI
    }
}

// Connect to a selected trainer
val connectionFlow: Flow<DataPacket>? = searchService.connectToDevice(selectedComponent)
```

### B. Observing Telemetry (Power, Cadence, Speed)
Once connected, client code can query the sensor by capability interface to collect the specific metrics needed. This keeps components loosely coupled (e.g., a power graph view only needs to depend on `PowerObservable` rather than a full smart trainer class).

```kotlin
fun subscribeToTelemetry(sensor: BLESensor<*>) {
    // 1. Observe Power
    if (sensor is PowerObservable) {
        scope.launch {
            sensor.powerFlow.collect { packet ->
                val watts = packet.data.getValueAsNumber()
                println("Current Power: $watts W")
            }
        }
    }

    // 2. Observe Cadence
    if (sensor is CadenceObservable) {
        scope.launch {
            sensor.cadenceFlow.collect { packet ->
                val rpm = packet.data.getValueAsNumber()
                println("Current Cadence: $rpm RPM")
            }
        }
    }

    // 3. Observe Speed
    if (sensor is SpeedObservable) {
        scope.launch {
            sensor.speedFlow.collect { packet ->
                val mph = packet.data.getValueAsNumber()
                println("Current Speed: $mph MPH")
            }
        }
    }
}
```

### C. Controlling Resistance (Applying Target Power)
To apply target resistance (e.g., when running a structured interval course in ERG mode), cast the device to `PowerControllable`. Control updates are suspendable and return a Kotlin `Result` to allow callers to handle failures natively.

```kotlin
suspend fun applyTargetPower(sensor: BLESensor<*>, targetWatts: Int) {
    if (sensor is PowerControllable) {
        sensor.setTargetPower(targetWatts)
            .onSuccess {
                println("Target power of $targetWatts W applied successfully.")
            }
            .onFailure { error ->
                System.err.println("Failed to write target power: ${error.message}")
            }
    } else {
        System.err.println("Connected device does not support resistance control.")
    }
}
```

---

## 3. Best Practice: Android Orchestrator Pattern

We recommend coordinating the connection, telemetry streams, and session lifecycle inside an orchestrating `ViewModel` or `Service`. 

Below is an implementation template demonstrating safe, structured coroutine handling:

```kotlin
class WorkoutViewModel : ViewModel() {
    private val searchService = AppComponent.get<BluetoothSearchService>()
    private val collectionService = AppComponent.get<DataCollectionService>()

    private val _uiState = MutableStateFlow<WorkoutUiState>(WorkoutUiState.Idle)
    val uiState = _uiState.asStateFlow()

    private var workoutJob: Job? = null

    /**
     * Connects to a selected sensor and syncs it with the collection session.
     */
    fun connectTrainer(device: BluetoothComponent) {
        viewModelScope.launch {
            _uiState.value = WorkoutUiState.Connecting
            searchService.connectToDevice(device)
        }
    }

    /**
     * Starts the workout session using an MRC file, collecting all telemetry.
     */
    fun startWorkout(mrcCourse: MrcCourse) {
        // Automatically syncs all connected sensors
        collectionService.beginSession(mrcCourse)

        workoutJob?.cancel()
        workoutJob = viewModelScope.launch {
            collectionService.collect().collect { packet ->
                updateUiWithPacket(packet)
            }
        }
    }

    private fun updateUiWithPacket(packet: DataPacket) {
        // Route telemetry updates to UI state
        when (packet) {
            is Power -> _uiState.update { it.copy(currentPower = packet.data.getValueAsNumber()) }
            is Cadence -> _uiState.update { it.copy(currentCadence = packet.data.getValueAsNumber()) }
            is Speed -> _uiState.update { it.copy(currentSpeed = packet.data.getValueAsNumber()) }
            // Handle other telemetry
            else -> {}
        }
    }

    override fun onCleared() {
        super.onCleared()
        collectionService.stopSession()
        searchService.disconnectToDevices()
    }
}
```
