# BluetoothSearchService: BLE Scan State Changes

// Developer Notes for BluetoothSearchService's scanDeviceState

/*
The `scanDeviceState` (a StateFlow<Operation>) in `BluetoothSearchService`
represents the current status of the Bluetooth device scanning process.
It transitions through a defined set of states based on scanning events.

**States and Transitions:**

1.  **Init (Initial State):**
    *   Represents the starting point of the scanning process.
    *   The `_scanDeviceState` MutableStateFlow is initialized to this state when the `BluetoothSearchService` is created.

2.  **ScanError:**
    *   Indicates that an error occurred during the scanning process, specifically a timeout.
    *   **Transition From:** `Init` (or any state while scanning is active).
    *   **Trigger:** Emitted if `startScanning()` runs for `SEARCH_TIMEOUT` (30 seconds) without discovering any new eligible devices. When this state is emitted, the `scopeSearch` coroutine scope is cancelled, effectively stopping the scanning.

3.  **OnDeviceUpdated(devices: List<BLESensor<*>>):**
    *   Signifies that the list of discovered Bluetooth devices has been updated. This typically occurs when a new eligible device is found during scanning.
    *   **Transition From:** `Init` (or any state while scanning is active and before a new device is found).
    *   **Trigger:** Emitted each time `scanner.advertisements.collectLatest` processes an advertisement that corresponds to a *new and eligible* Bluetooth device. A device is considered eligible if its UUIDs match any of the `BLEDeviceUtil.serviceTags` and it's not already in the `remoteDevices` map. The `devices` parameter of this state contains the current list of all discovered `BLESensor` objects. If a new device is found and this state is emitted, the timeout for scanning is cancelled, allowing the scan to continue indefinitely or until explicitly stopped.

**Note on OnDeviceCollectionUpdated:**
The `OnDeviceCollectionUpdated` state is defined within the `Operation` sealed interface but is not currently utilized (emitted) by the `BluetoothSearchService` itself in the provided code. It may be intended for use in other parts of the application that consume this service.

**Important Considerations for Observers:**
*   `scanDeviceState` is a `StateFlow`, meaning observers will always receive the latest state upon subscription and subsequent state changes.
*   The `ScanError` state effectively terminates the current scanning operation due to a timeout.
*   The `OnDeviceUpdated` state provides a continuous stream of discovered devices as they are found.
*   The `Connected` state (from `com.skjline.fitness.core.model.state.Connected`) is related to the individual `BLESensor` connectivity, not the overall `scanDeviceState` of the `BluetoothSearchService`. To observe individual device connection status, one would typically observe the `connectivity` property of a specific `BLESensor` object.
*/

/*
**Individual Device Connectivity States (BLESensor.connectivity)**

In addition to the overall scanning states managed by `BluetoothSearchService.scanDeviceState`,
each individual `BLESensor` object (representing a discovered Bluetooth device)
manages its own `connectivity` StateFlow, which reflects its current connection status.
These states are defined in the `Connectivity` sealed interface (`Connectivity.kt`):

1.  **Discovered (Initial Connectivity State):**
    *   Represents the initial state of a `BLESensor` object immediately after it has been identified and instantiated by the `BluetoothSearchService`.
    *   **Trigger:** A `BLESensor`'s `_connectivity` MutableStateFlow is initialized to `Discovered` within its constructor, meaning a device enters this state as soon as it's recognized as a valid BLE sensor.

2.  **Connecting:**
    *   Indicates that a connection attempt to the device is in progress.

3.  **Connected:**
    *   Signifies that the device is successfully connected.

4.  **Disconnecting:**
    *   Indicates that the device is in the process of disconnecting.

5.  **Disconnected:**
    *   Represents a device that is not currently connected.

**Relationship between scanDeviceState and connectivity:**

*   `BluetoothSearchService.scanDeviceState` tracks the *process of discovering* devices. When `OnDeviceUpdated` is emitted, it means new `BLESensor` objects have been created and added to the list.
*   Each of these newly created `BLESensor` objects will inherently be in the `Discovered` connectivity state.
*   The `connectivity` state of an individual `BLESensor` then changes independently based on explicit connection/disconnection requests (e.g., via `BluetoothSearchService.connectToDevice` or `disconnectToDevices`) and the underlying BLE peripheral's state changes.
*/

---

# Sensor Progress Manuals

Here are the progress manuals for individual sensor implementations, detailing their responsibilities, data flow, and current state.

### **BikeTrainerSensor Progress Manual**

The `BikeTrainerSensor` is a comprehensive sensor designed to interact with Bluetooth LE fitness machines (bike trainers) supporting Cycling Power and Fitness Machine GATT services. It serves as a central interface for controlling the trainer and receiving various data streams.

**Key Functionality & Data Flow:**

1.  **Specialized Characteristics:**
    *   Utilizes `powerCharacteristic` for receiving Cycling Power Measurement data.
    *   Employs `controlCharacteristic` for sending commands to the fitness machine.

2.  **Connection and Control:**
    *   Upon connection, it first requests control of the fitness machine using `FMCP_REQUEST_CONTROL`. This is a prerequisite for sending further commands.
    *   It then initiates observation of the `powerCharacteristic` to continuously receive data.

3.  **Data Processing and Emission:**
    *   Receives raw bytes from the `powerCharacteristic` and parses them into `CyclingPowerMeasurementPacket`s.
    *   Calculates **Cadence (RPM)** from the crank data within the `CyclingPowerMeasurementPacket`.
    *   Extracts **Power** level from the `CyclingPowerMeasurementPacket`.
    *   Both calculated Cadence (wrapped as a `Cadence` packet) and extracted Power (wrapped as a `Power` packet) are emitted via its `observer` (a `MutableStateFlow<DataPacket>`).

4.  **Control Capabilities:**
    *   Responds to various `Action` commands, including `Start`, `Stop`, `Pause`, and `SetTargetPower`.
    *   Each action translates into specific Fitness Machine Control Point (FMCP) byte commands sent to the `controlCharacteristic`.

5.  **Provided Data Types:**
    *   Declares that it provides `PacketType.Power` and `PacketType.Cadence`.

**Summary of Progress:**
The `BikeTrainerSensor` actively manages the connection, asserts control over the fitness machine, continuously streams and processes performance data (power and cadence), and allows for real-time control (start, stop, pause, target power adjustment).

---

### **HRMSensor Progress Manual**

The `HRMSensor` is designed for passive data collection from Bluetooth LE Heart Rate Monitors (HRM) that implement the Heart Rate GATT service. It focuses solely on receiving and reporting heart rate data.

**Key Functionality & Data Flow:**

1.  **Specialized Characteristic:**
    *   Observes the `Heart_Rate_Measurement` characteristic within the `Heart_Rate` service.

2.  **Connection and Observation:**
    *   Upon connection, it immediately starts observing the `Heart_Rate_Measurement` characteristic for incoming heart rate data.

3.  **Data Processing and Emission:**
    *   Receives raw bytes from the characteristic and parses them into `HeartRatePacket`s.
    *   Wraps the `HeartRatePacket` into `HeartRateContent` and then into an `HRData` packet, which is then emitted via its `observer` (a `MutableStateFlow<HRData>`).

4.  **Read-Only Operation:**
    *   Both `writeDescription()` and `request(action: Action)` methods are empty, indicating that this sensor does not send commands or control the HRM device; it only receives data.

5.  **Provided Data Types:**
    *   Declares that it provides `PacketType.HRData`.

**Summary of Progress:**
The `HRMSensor` provides continuous, real-time heart rate data by passively observing the HRM's measurement characteristic, making it a dedicated heart rate data source.

---

### **CadenceSensor Progress Manual**

The `CadenceSensor` is intended to provide cadence data from Bluetooth LE devices that support the Cycling Power Service. It focuses on extracting cadence information from the generic cycling power measurement data.

**Key Functionality & Data Flow:**

1.  **Specialized Characteristic:**
    *   Observes the `Cycling_Power_Measurement` characteristic within the `Cycling_Power` service.

2.  **Connection and Observation:**
    *   Upon connection, it initiates observation of the `Cycling_Power_Measurement` characteristic.

3.  **Data Processing and Emission:**
    *   Receives raw bytes from the characteristic and parses them into `CyclingPowerMeasurementPacket`s. These packets contain various cycling metrics, including cadence.
    *   It then wraps the `CyclingPowerMeasurementPacket` into `PowerContent` and emits it as a `Power` packet via its `observer` (a `MutableStateFlow<Power>`).
    *   **Note:** While the class is named `CadenceSensor` and declares `PacketType.Cadence`, it emits a `Power` packet. Consumers are expected to extract cadence information from the `CyclingPowerMeasurementPacket` contained within this `Power` packet.

4.  **Limited Interaction:**
    *   `writeDescription()` is empty.
    *   The `request(action: Action)` method only handles `Connect` by calling its own `connect()`. Other actions are ignored.

5.  **Provided Data Types:**
    *   Declares that it provides `PacketType.Cadence`.

**Summary of Progress:**
The `CadenceSensor` continuously monitors cycling power measurements, parses the incoming data, and makes cadence information available to observers (embedded within `Power` packets).

---

### **PowerSensor Progress Manual**

The `PowerSensor` is dedicated to providing power data from Bluetooth LE devices that support the Cycling Power Service. It focuses on extracting and reporting power measurements.

**Key Functionality & Data Flow:**

1.  **Specialized Characteristic:**
    *   Observes the `Cycling_Power_Measurement` characteristic within the `Cycling_Power` service.

2.  **Connection and Observation:**
    *   Upon connection, it initiates observation of the `Cycling_Power_Measurement` characteristic.

3.  **Data Processing and Emission:**
    *   Receives raw bytes from the characteristic and parses them into `CyclingPowerMeasurementPacket`s.
    *   It then wraps the `CyclingPowerMeasurementPacket` into `PowerContent` and emits it as a `Power` packet via its `observer` (a `MutableStateFlow<Power>`). This aligns with its name and purpose.

4.  **Limited Interaction:**
    *   `writeDescription()` is empty.
    *   The `request(action: Action)` method only handles `Connect` by calling its own `connect()`. Other actions are ignored.

5.  **Provided Data Types:**
    *   Declares that it provides `PacketType.Power`.

**Summary of Progress:**
The `PowerSensor` continuously monitors cycling power measurements, parses the incoming data, and makes power information available to observers.

---

### **SpeedSensor Progress Manual**

The `SpeedSensor` is intended to provide speed data. However, as currently implemented, it is **incomplete** and does not actively collect or process speed data.

**Key Functionality & Data Flow (Current State):**

1.  **Initialization:**
    *   The `observer` (a `MutableStateFlow<Power>`) is initialized to a default `Power` packet. The type mismatch (emitting `Power` for a `SpeedSensor`) suggests this is a placeholder.

2.  **Connection (Incomplete):**
    *   The `connect()` method overrides the base implementation but **lacks any code to observe BLE characteristics or process incoming data**. It only calls `super.connect()`.

3.  **Data Processing and Emission:**
    *   There is **no active mechanism** to receive, parse, or emit speed data from the BLE peripheral. The `observer` will remain at its initial state.

4.  **Limited Interaction:**
    *   `writeDescription()` is empty.
    *   The `request(action: Action)` method only handles `Connect` by calling its own `connect()`. Other actions are ignored.

5.  **Provided Data Types:**
    *   Declares that it provides `PacketType.Speed`. This is the only clear indication of its intended purpose.

**Summary of Progress:**
The `SpeedSensor` is a placeholder. It currently lacks the necessary implementation to connect to a BLE speed characteristic, collect data, or provide any speed measurements. It requires further development to become functional.
