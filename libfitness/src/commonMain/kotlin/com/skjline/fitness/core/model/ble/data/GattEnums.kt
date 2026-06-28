package com.skjline.fitness.core.model.ble.data

enum class GattService(val uuid: String) {
    Generic_Access("00001800-0000-1000-8000-00805f9b34fb"),
    Generic_Attribute("00001801-0000-1000-8000-00805f9b34fb"),
    Device_Name("00002a00-0000-1000-8000-00805f9b34fb"),
    Model_Name_String("00002a04-0000-1000-8000-00805f9b34fb"),
    Manufacturer_Name_String("00002a29-0000-1000-8000-00805f9b34fb"),

    Cycling_Speed_and_Cadence("00001816-0000-1000-8000-00805f9b34fb"),
    Cycling_Power("00001818-0000-1000-8000-00805f9b34fb"),
    Heart_Rate("0000180d-0000-1000-8000-00805f9b34fb"),

    Device_Information("0000180a-0000-1000-8000-00805f9b34fb"),

    Tx_Power("00001804-0000-1000-8000-00805f9b34fb"),
    Fitness_Machine("00001826-0000-1000-8000-00805f9b34fb"),

    Immediate_Alert("00001802-0000-1000-8000-00805f9b34fb"),
    Link_Loss("00001803-0000-1000-8000-00805f9b34fb"),
    Current_Time_Service("00001805-0000-1000-8000-00805f9b34fb"),
    Reference_Time_Update_Service("00001806-0000-1000-8000-00805f9b34fb"),
    Next_DST_Change_Service("00001807-0000-1000-8000-00805f9b34fb"),
    Glucose("00001808-0000-1000-8000-00805f9b34fb"),
    Health_Thermometer("00001809-0000-1000-8000-00805f9b34fb"),
    Phone_Alert_Status_Service("0000180e-0000-1000-8000-00805f9b34fb"),
    Battery_Service("0000180f-0000-1000-8000-00805f9b34fb"),
    Blood_Pressure("00001810-0000-1000-8000-00805f9b34fb"),
    Alert_Notification_Service("00001811-0000-1000-8000-00805f9b34fb"),
    Human_Interface_Device("00001812-0000-1000-8000-00805f9b34fb"),
    Scan_Parameters("00001813-0000-1000-8000-00805f9b34fb"),
    Running_Speed_and_Cadence("00001814-0000-1000-8000-00805f9b34fb"),
    Automation_IO("00001815-0000-1000-8000-00805f9b34fb"),
    Location_and_Navigation("00001819-0000-1000-8000-00805f9b34fb"),
    Environmental_Sensing("0000181a-0000-1000-8000-00805f9b34fb"),
    Body_Composition("0000181b-0000-1000-8000-00805f9b34fb"),
    User_Data("0000181c-0000-1000-8000-00805f9b34fb"),
    Weight_Scale("0000181d-0000-1000-8000-00805f9b34fb"),
    Bond_Management_Service("0000181e-0000-1000-8000-00805f9b34fb"),
    Continuous_Glucose_Monitoring("0000181f-0000-1000-8000-00805f9b34fb"),
    Internet_Protocol_Support_Service("00001820-0000-1000-8000-00805f9b34fb"),
    Indoor_Positioning("00001821-0000-1000-8000-00805f9b34fb"),
    Pulse_Oximeter_Service("00001822-0000-1000-8000-00805f9b34fb"),
    HTTP_Proxy("00001823-0000-1000-8000-00805f9b34fb"),
    Transport_Discovery("00001824-0000-1000-8000-00805f9b34fb"),
    Object_Transfer_Service("00001825-0000-1000-8000-00805f9b34fb"),
    Mesh_Provisioning_Service("00001827-0000-1000-8000-00805f9b34fb"),
    Mesh_Proxy_Service("00001828-0000-1000-8000-00805f9b34fb"),
    Reconnection_Configuration("00001829-0000-1000-8000-00805f9b34fb"),
    Appearance("00002a01-0000-1000-8000-00805f9b34fb"),
    Peripheral_Privacy_Flag("00002a02-0000-1000-8000-00805f9b34fb"),
    Reconnection_Address("00002a03-0000-1000-8000-00805f9b34fb"),
    Service_Changed("00002a05-0000-1000-8000-00805f9b34fb"),
    Alert_level("00002a06-0000-1000-8000-00805f9b34fb"),
    Unknown(""),
    ;
}

enum class GattCharacteristic(val uuid: String) {
    Device_Name("00002a00-0000-1000-8000-00805f9b34fb"),
    Appearance("00002a01-0000-1000-8000-00805f9b34fb"),
    Peripheral_Privacy_Flag("00002a02-0000-1000-8000-00805f9b34fb"),
    Reconnection_Address("00002a03-0000-1000-8000-00805f9b34fb"),
    Peripheral_Preferred_Connection_Parameters("00002a04-0000-1000-8000-00805f9b34fb"),
    Service_Changed("00002a05-0000-1000-8000-00805f9b34fb"),
    Alert_Level("00002a06-0000-1000-8000-00805f9b34fb"),
    Tx_Power_Level("00002a07-0000-1000-8000-00805f9b34fb"),
    Date_Time("00002a08-0000-1000-8000-00805f9b34fb"),
    Day_of_Week("00002a09-0000-1000-8000-00805f9b34fb"),
    Day_Date_Time("00002a0a-0000-1000-8000-00805f9b34fb"),
    Exact_Time_100("00002a0b-0000-1000-8000-00805f9b34fb"),
    Exact_Time_256("00002a0c-0000-1000-8000-00805f9b34fb"),
    DST_Offset("00002a0d-0000-1000-8000-00805f9b34fb"),
    Time_Zone("00002a0e-0000-1000-8000-00805f9b34fb"),
    Local_Time_Information("00002a0f-0000-1000-8000-00805f9b34fb"),
    Secondary_Time_Zone("00002a10-0000-1000-8000-00805f9b34fb"),
    Time_with_DST("00002a11-0000-1000-8000-00805f9b34fb"),
    Time_Accuracy("00002a12-0000-1000-8000-00805f9b34fb"),
    Time_Source("00002a13-0000-1000-8000-00805f9b34fb"),
    Reference_Time_Information("00002a14-0000-1000-8000-00805f9b34fb"),
    Time_Broadcast("00002a15-0000-1000-8000-00805f9b34fb"),
    Time_Update_Control_Point("00002a16-0000-1000-8000-00805f9b34fb"),
    Time_Update_State("00002a17-0000-1000-8000-00805f9b34fb"),
    Glucose_Measurement("00002a18-0000-1000-8000-00805f9b34fb"),
    Battery_Level("00002a19-0000-1000-8000-00805f9b34fb"),
    Battery_Power_State("00002a1a-0000-1000-8000-00805f9b34fb"),
    Battery_Level_State("00002a1b-0000-1000-8000-00805f9b34fb"),
    Temperature_Measurement("00002a1c-0000-1000-8000-00805f9b34fb"),
    Temperature_Type("00002a1d-0000-1000-8000-00805f9b34fb"),
    Intermediate_Temperature("00002a1e-0000-1000-8000-00805f9b34fb"),
    Temperature_Celsius("00002a1f-0000-1000-8000-00805f9b34fb"),
    Temperature_Fahrenheit("00002a20-0000-1000-8000-00805f9b34fb"),
    Measurement_Interval("00002a21-0000-1000-8000-00805f9b34fb"),
    Boot_Keyboard_Input_Report("00002a22-0000-1000-8000-00805f9b34fb"),
    System_ID("00002a23-0000-1000-8000-00805f9b34fb"),
    Model_Number("00002a24-0000-1000-8000-00805f9b34fb"),
    Serial_Number("00002a25-0000-1000-8000-00805f9b34fb"),
    Firmware_Revision("00002a26-0000-1000-8000-00805f9b34fb"),
    Hardware_Revision("00002a27-0000-1000-8000-00805f9b34fb"),
    Software_Revision("00002a28-0000-1000-8000-00805f9b34fb"),
    Manufacturer_Name("00002a29-0000-1000-8000-00805f9b34fb"),
    IEEE_11073_20601_Regulatory_Certification("00002a2a-0000-1000-8000-00805f9b34fb"),
    Current_Time("00002a2b-0000-1000-8000-00805f9b34fb"),
    Magnetic_Declination("00002a2c-0000-1000-8000-00805f9b34fb"),
    Position_2D("00002a2f-0000-1000-8000-00805f9b34fb"),
    Position_3D("00002a30-0000-1000-8000-00805f9b34fb"),
    Scan_Refresh("00002a31-0000-1000-8000-00805f9b34fb"),
    Boot_Keyboard_Output_Report("00002a32-0000-1000-8000-00805f9b34fb"),
    Boot_Mouse_Input_Report("00002a33-0000-1000-8000-00805f9b34fb"),
    Glucose_Measurement_Context("00002a34-0000-1000-8000-00805f9b34fb"),
    Blood_Pressure_Measurement("00002a35-0000-1000-8000-00805f9b34fb"),
    Intermediate_Cuff_Pressure("00002a36-0000-1000-8000-00805f9b34fb"),
    Heart_Rate_Measurement("00002a37-0000-1000-8000-00805f9b34fb"),
    Body_Sensor_Location("00002a38-0000-1000-8000-00805f9b34fb"),
    Heart_Rate_Control_Point("00002a39-0000-1000-8000-00805f9b34fb"),
    Removable("00002a3a-0000-1000-8000-00805f9b34fb"),
    Service_Required("00002a3b-0000-1000-8000-00805f9b34fb"),
    Scientific_Temperature_Celsius("00002a3c-0000-1000-8000-00805f9b34fb"),
    GATTString("00002a3d-0000-1000-8000-00805f9b34fb"),
    Network_Availability("00002a3e-0000-1000-8000-00805f9b34fb"),
    Alert_Status("00002a3f-0000-1000-8000-00805f9b34fb"),
    Ringer_Control_Point("00002a40-0000-1000-8000-00805f9b34fb"),
    Ringer_Setting("00002a41-0000-1000-8000-00805f9b34fb"),
    Alert_Category_ID_Bit_Mask("00002a42-0000-1000-8000-00805f9b34fb"),
    Alert_Category_ID("00002a43-0000-1000-8000-00805f9b34fb"),
    Alert_Notification_Control_Point("00002a44-0000-1000-8000-00805f9b34fb"),
    Unread_Alert_Status("00002a45-0000-1000-8000-00805f9b34fb"),
    New_Alert("00002a46-0000-1000-8000-00805f9b34fb"),
    Supported_New_Alert_Category("00002a47-0000-1000-8000-00805f9b34fb"),
    Supported_Unread_Alert_Category("00002a48-0000-1000-8000-00805f9b34fb"),
    Blood_Pressure_Feature("00002a49-0000-1000-8000-00805f9b34fb"),
    HID_Information("00002a4a-0000-1000-8000-00805f9b34fb"),
    Report_Map("00002a4b-0000-1000-8000-00805f9b34fb"),
    HID_Control_Point("00002a4c-0000-1000-8000-00805f9b34fb"),
    Report("00002a4d-0000-1000-8000-00805f9b34fb"),
    Protocol_Mode("00002a4e-0000-1000-8000-00805f9b34fb"),
    Scan_Interval_Window("00002a4f-0000-1000-8000-00805f9b34fb"),
    PnP_ID("00002a50-0000-1000-8000-00805f9b34fb"),
    Glucose_Feature("00002a51-0000-1000-8000-00805f9b34fb"),
    Record_Access_Control_Point("00002a52-0000-1000-8000-00805f9b34fb"),
    RSC_Measurement("00002a53-0000-1000-8000-00805f9b34fb"),
    RSC_Feature("00002a54-0000-1000-8000-00805f9b34fb"),
    SC_Control_Point("00002a55-0000-1000-8000-00805f9b34fb"),
    Digital("00002a56-0000-1000-8000-00805f9b34fb"),
    Digital_Output("00002a57-0000-1000-8000-00805f9b34fb"),
    Analog("00002a58-0000-1000-8000-00805f9b34fb"),
    Analog_Output("00002a59-0000-1000-8000-00805f9b34fb"),
    Aggregate("00002a5a-0000-1000-8000-00805f9b34fb"),
    CSC_Measurement("00002a5b-0000-1000-8000-00805f9b34fb"),
    CSC_Feature("00002a5c-0000-1000-8000-00805f9b34fb"),
    Sensor_Location("00002a5d-0000-1000-8000-00805f9b34fb"),
    PLX_Spot_Check_Measurement("00002a5e-0000-1000-8000-00805f9b34fb"),
    PLX_Continuous_Measurement_Characteristic("00002a5f-0000-1000-8000-00805f9b34fb"),
    PLX_Features("00002a60-0000-1000-8000-00805f9b34fb"),
    Pulse_Oximetry_Control_Point("00002a62-0000-1000-8000-00805f9b34fb"),
    Cycling_Power_Measurement("00002a63-0000-1000-8000-00805f9b34fb"),
    Cycling_Power_Vector("00002a64-0000-1000-8000-00805f9b34fb"),
    Cycling_Power_Feature("00002a65-0000-1000-8000-00805f9b34fb"),
    Cycling_Power_Control_Point("00002a66-0000-1000-8000-00805f9b34fb"),
    Location_and_Speed_Characteristic("00002a67-0000-1000-8000-00805f9b34fb"),
    Navigation("00002a68-0000-1000-8000-00805f9b34fb"),
    Position_Quality("00002a69-0000-1000-8000-00805f9b34fb"),
    LN_Feature("00002a6a-0000-1000-8000-00805f9b34fb"),
    LN_Control_Point("00002a6b-0000-1000-8000-00805f9b34fb"),
    Elevation("00002a6c-0000-1000-8000-00805f9b34fb"),
    Pressure("00002a6d-0000-1000-8000-00805f9b34fb"),
    Temperature("00002a6e-0000-1000-8000-00805f9b34fb"),
    Humidity("00002a6f-0000-1000-8000-00805f9b34fb"),
    True_Wind_Speed("00002a70-0000-1000-8000-00805f9b34fb"),
    True_Wind_Direction("00002a71-0000-1000-8000-00805f9b34fb"),
    Apparent_Wind_Speed("00002a72-0000-1000-8000-00805f9b34fb"),
    Apparent_Wind_Direction("00002a73-0000-1000-8000-00805f9b34fb"),
    Gust_Factor("00002a74-0000-1000-8000-00805f9b34fb"),
    Pollen_Concentration("00002a75-0000-1000-8000-00805f9b34fb"),
    UV_Index("00002a76-0000-1000-8000-00805f9b34fb"),
    Irradiance("00002a77-0000-1000-8000-00805f9b34fb"),
    Rainfall("00002a78-0000-1000-8000-00805f9b34fb"),
    Wind_Chill("00002a79-0000-1000-8000-00805f9b34fb"),
    Heat_Index("00002a7a-0000-1000-8000-00805f9b34fb"),
    Dew_Point("00002a7b-0000-1000-8000-00805f9b34fb"),
    Descriptor_Value_Changed("00002a7d-0000-1000-8000-00805f9b34fb"),
    Aerobic_Heart_Rate_Lower_Limit("00002a7e-0000-1000-8000-00805f9b34fb"),
    Aerobic_Threshold("00002a7f-0000-1000-8000-00805f9b34fb"),
    Age("00002a80-0000-1000-8000-00805f9b34fb"),
    Anaerobic_Heart_Rate_Lower_Limit("00002a81-0000-1000-8000-00805f9b34fb"),
    Anaerobic_Heart_Rate_Upper_Limit("00002a82-0000-1000-8000-00805f9b34fb"),
    Anaerobic_Threshold("00002a83-0000-1000-8000-00805f9b34fb"),
    Aerobic_Heart_Rate_Upper_Limit("00002a84-0000-1000-8000-00805f9b34fb"),
    Date_of_Birth("00002a85-0000-1000-8000-00805f9b34fb"),
    Date_of_Threshold_Assessment("00002a86-0000-1000-8000-00805f9b34fb"),
    Email_Address("00002a87-0000-1000-8000-00805f9b34fb"),
    Fat_Burn_Heart_Rate_Lower_Limit("00002a88-0000-1000-8000-00805f9b34fb"),
    Fat_Burn_Heart_Rate_Upper_Limit("00002a89-0000-1000-8000-00805f9b34fb"),
    First_Name("00002a8a-0000-1000-8000-00805f9b34fb"),
    Five_Zone_Heart_Rate_Limits("00002a8b-0000-1000-8000-00805f9b34fb"),
    Gender("00002a8c-0000-1000-8000-00805f9b34fb"),
    Heart_Rate_Max("00002a8d-0000-1000-8000-00805f9b34fb"),
    Height("00002a8e-0000-1000-8000-00805f9b34fb"),
    Hip_Circumference("00002a8f-0000-1000-8000-00805f9b34fb"),
    Last_Name("00002a90-0000-1000-8000-00805f9b34fb"),
    Maximum_Recommended_Heart_Rate("00002a91-0000-1000-8000-00805f9b34fb"),
    Resting_Heart_Rate("00002a92-0000-1000-8000-00805f9b34fb"),
    Sport_Type_for_Aerobic_and_Anaerobic_Thresholds("00002a93-0000-1000-8000-00805f9b34fb"),
    Three_Zone_Heart_Rate_Limits("00002a94-0000-1000-8000-00805f9b34fb"),
    Two_Zone_Heart_Rate_Limit("00002a95-0000-1000-8000-00805f9b34fb"),
    VO2_Max("00002a96-0000-1000-8000-00805f9b34fb"),
    Waist_Circumference("00002a97-0000-1000-8000-00805f9b34fb"),
    Weight("00002a98-0000-1000-8000-00805f9b34fb"),
    Database_Change_Increment("00002a99-0000-1000-8000-00805f9b34fb"),
    User_Index("00002a9a-0000-1000-8000-00805f9b34fb"),
    Body_Composition_Feature("00002a9b-0000-1000-8000-00805f9b34fb"),
    Body_Composition_Measurement("00002a9c-0000-1000-8000-00805f9b34fb"),
    Weight_Measurement("00002a9d-0000-1000-8000-00805f9b34fb"),
    Weight_Scale_Feature("00002a9e-0000-1000-8000-00805f9b34fb"),
    User_Control_Point("00002a9f-0000-1000-8000-00805f9b34fb"),
    Magnetic_Flux_Density_2D("00002aa0-0000-1000-8000-00805f9b34fb"),
    Magnetic_Flux_Density_3D("00002aa1-0000-1000-8000-00805f9b34fb"),
    Language("00002aa2-0000-1000-8000-00805f9b34fb"),
    Barometric_Pressure_Trend("00002aa3-0000-1000-8000-00805f9b34fb"),
    Bond_Management_Control_Point("00002aa4-0000-1000-8000-00805f9b34fb"),
    Bond_Management_Features("00002aa5-0000-1000-8000-00805f9b34fb"),
    Central_Address_Resolution("00002aa6-0000-1000-8000-00805f9b34fb"),
    CGM_Measurement("00002aa7-0000-1000-8000-00805f9b34fb"),
    CGM_Feature("00002aa8-0000-1000-8000-00805f9b34fb"),
    CGM_Status("00002aa9-0000-1000-8000-00805f9b34fb"),
    CGM_Session_Start_Time("00002aaa-0000-1000-8000-00805f9b34fb"),
    CGM_Session_Run_Time("00002aab-0000-1000-8000-00805f9b34fb"),
    CGM_Specific_Ops_Control_Point("00002aac-0000-1000-8000-00805f9b34fb"),
    Indoor_Positioning_Configuration("00002aad-0000-1000-8000-00805f9b34fb"),
    Latitude("00002aae-0000-1000-8000-00805f9b34fb"),
    Longitude("00002aaf-0000-1000-8000-00805f9b34fb"),
    Local_North_Coordinate("00002ab0-0000-1000-8000-00805f9b34fb"),
    Local_East_Coordinate("00002ab1-0000-1000-8000-00805f9b34fb"),
    Floor_Number("00002ab2-0000-1000-8000-00805f9b34fb"),
    Altitude("00002ab3-0000-1000-8000-00805f9b34fb"),
    Uncertainty("00002ab4-0000-1000-8000-00805f9b34fb"),
    Location_Name("00002ab5-0000-1000-8000-00805f9b34fb"),
    URI("00002ab6-0000-1000-8000-00805f9b34fb"),
    HTTP_Headers("00002ab7-0000-1000-8000-00805f9b34fb"),
    HTTP_Status_Code("00002ab8-0000-1000-8000-00805f9b34fb"),
    HTTP_Entity_Body("00002ab9-0000-1000-8000-00805f9b34fb"),
    HTTP_Control_Point("00002aba-0000-1000-8000-00805f9b34fb"),
    HTTPS_Security("00002abb-0000-1000-8000-00805f9b34fb"),
    TDS_Control_Point("00002abc-0000-1000-8000-00805f9b34fb"),
    OTS_Feature("00002abd-0000-1000-8000-00805f9b34fb"),
    Object_Name("00002abe-0000-1000-8000-00805f9b34fb"),
    Object_Type("00002abf-0000-1000-8000-00805f9b34fb"),
    Object_Size("00002ac0-0000-1000-8000-00805f9b34fb"),
    Object_First_Created("00002ac1-0000-1000-8000-00805f9b34fb"),
    Object_Last_Modified("00002ac2-0000-1000-8000-00805f9b34fb"),
    Object_ID("00002ac3-0000-1000-8000-00805f9b34fb"),
    Object_Properties("00002ac4-0000-1000-8000-00805f9b34fb"),
    Object_Action_Control_Point("00002ac5-0000-1000-8000-00805f9b34fb"),
    Object_List_Control_Point("00002ac6-0000-1000-8000-00805f9b34fb"),
    Object_List_Filter("00002ac7-0000-1000-8000-00805f9b34fb"),
    Object_Changed("00002ac8-0000-1000-8000-00805f9b34fb"),
    Resolvable_Private_Address_Only("00002ac9-0000-1000-8000-00805f9b34fb"),
    Fitness_Machine_Feature("00002acc-0000-1000-8000-00805f9b34fb"),
    Treadmill_Data("00002acd-0000-1000-8000-00805f9b34fb"),
    Cross_Trainer_Data("00002ace-0000-1000-8000-00805f9b34fb"),
    Step_Climber_Data("00002acf-0000-1000-8000-00805f9b34fb"),
    Stair_Climber_Data("00002ad0-0000-1000-8000-00805f9b34fb"),
    Rower_Data("00002ad1-0000-1000-8000-00805f9b34fb"),
    Indoor_Bike_Data("00002ad2-0000-1000-8000-00805f9b34fb"),
    Training_Status("00002ad3-0000-1000-8000-00805f9b34fb"),
    Supported_Speed_Range("00002ad4-0000-1000-8000-00805f9b34fb"),
    Supported_Inclination_Range("00002ad5-0000-1000-8000-00805f9b34fb"),
    Supported_Resistance_Level_Range("00002ad6-0000-1000-8000-00805f9b34fb"),
    Supported_Heart_Rate_Range("00002ad7-0000-1000-8000-00805f9b34fb"),
    Supported_Power_Range("00002ad8-0000-1000-8000-00805f9b34fb"),
    Fitness_Machine_Control_Point("00002ad9-0000-1000-8000-00805f9b34fb"),
    Fitness_Machine_Status("00002ada-0000-1000-8000-00805f9b34fb"),
    Date_UTC("00002aed-0000-1000-8000-00805f9b34fb"),
    RC_Feature("00002b1d-0000-1000-8000-00805f9b34fb"),
    RC_Settings("00002b1e-0000-1000-8000-00805f9b34fb"),
    Reconnection_Configuration_Control_Poin("00002b1f-0000-1000-8000-00805f9b34fb"),
    ;
}

enum class GattDescriptors(val uuid: String) {
    Characteristic_Extended_Properties("00002900-0000-1000-8000-00805f9b34fb"),
    Characteristic_User_Description("00002901-0000-1000-8000-00805f9b34fb"),
    Client_Characteristic_Configuration("00002902-0000-1000-8000-00805f9b34fb"),
    Server_Characteristic_Configuration("00002903-0000-1000-8000-00805f9b34fb"),
    Characteristic_Presentation_Format("00002904-0000-1000-8000-00805f9b34fb"),
    Characteristic_Aggregate_Format("00002905-0000-1000-8000-00805f9b34fb"),
    Valid_Range("00002906-0000-1000-8000-00805f9b34fb"),
    External_Report_Reference("00002907-0000-1000-8000-00805f9b34fb"),
    Report_Reference("00002908-0000-1000-8000-00805f9b34fb"),
    Number_of_Digitals("00002909-0000-1000-8000-00805f9b34fb"),
    Value_Trigger_Setting("0000290a-0000-1000-8000-00805f9b34fb"),
    Environmental_Sensing_Configuration("0000290b-0000-1000-8000-00805f9b34fb"),
    Environmental_Sensing_Measurement("0000290c-0000-1000-8000-00805f9b34fb"),
    Environmental_Sensing_Trigger_Setting("0000290d-0000-1000-8000-00805f9b34fb"),
    Time_Trigger_Setting("0000290e-0000-1000-8000-00805f9b34fb"),
    ;
}


//fun BluetoothGattService.getServiceOf() =
//    uuid.getServiceOf()
//
//fun UUID.getServiceOf() =
//    toString().getServiceOf()
//
fun String.getServiceOf() =
    GattService.entries.firstOrNull { it.uuid == this }
//
//fun BluetoothGattCharacteristic.getCharacteristicsOf() =
//    uuid.getCharacteristicsOf()

//fun UUID.getCharacteristicsOf() =
//    toString().getCharacteristicsOf()

fun String.getCharacteristicsOf() =
    GattCharacteristic.entries.firstOrNull { it.uuid == this }


enum class GattWriteErrorCode(val code: Int) {
    GATT_SUCCESS(0),
    GATT_READ_NOT_PERMITTED(2),
    GATT_WRITE_NOT_PERMITTED(3),
    GATT_INSUFFICIENT_AUTHENTICATION(5),
    GATT_REQUEST_NOT_SUPPORTED(6),
    GATT_INVALID_OFFSET(7),
    GATT_INSUFFICIENT_AUTHORIZATION(8),
    GATT_INVALID_ATTRIBUTE_LENGTH(13),
    GATT_INSUFFICIENT_ENCRYPTION(15),
    GATT_CONNECTION_CONGESTED(143),
    GATT_FAILURE(257),
    ;
}

enum class ControlPoint(val value: Byte) {
    Request_Control(0),
    Reset(1),
    Set_Target_Speed(2),
    Set_Target_Inclination(3),
    Set_Target_Resistance_Level(4),
    Set_Target_Power(5),
    Set_Target_Heart_Rate(6),
    Start_Resume(7),
    Stop_Pause(8),
    Set_Targeted_Expended_Energy(9),
    Set_Targeted_Number_Steps(10),
    Set_Targeted_Number_Strides(11),
    Set_Targeted_Distance(12),
    Set_Targeted_Training_Time(13),
    Set_Targeted_2_Heart_Rate_Zone(14),
    Set_Targeted_3_Heart_Rate_Zone(15),
    Set_Targeted_5_Heart_Rate_Zone(16),
    Set_Indoor_Bike_Simulation(17),
    Set_Wheel_Circumference(18),
    Spin_Down_Control(19),
    Set_Targeted_Cadence(20),
    Response_Code(0x80.toByte()),
    ;
}

class FeatureValue {
    companion object {
        const val AVG_SPEED = 0x01
        const val CADENCE = 0x02
        const val TOTAL_DISTANCE = 0x04
        const val INCLINE = 0x08
        const val ELEVATION_GAIN = 0x10
        const val PACE = 0x20
        const val STEP = 0x40
        const val RESISTANCE = 0x80

        const val STRIDE = 0x01
        const val EXP_ENERGY = 0x02
        const val HEART_RATE = 0x04
        const val METABOLIC = 0x08
        const val ELAPSED_TIME = 0x10
        const val REMAINING_TIME = 0x20
        const val POWER_MEASUREMENT = 0x40
        const val FORCE_ON_BELT = 0x80

        const val SPEED_TARGET = 0x01
        const val INCLINE_TARGET = 0x02
        const val RESIST_TARGET = 0x04
        const val POWER_TARGET = 0x08
        const val HR_TARGET = 0x10
        const val EXP_ENERGY_CFG = 0x20
        const val TARGET_STE_NO = 0x40
        const val TARGET_STRIDE_NO = 0x80

        const val TOTAL_DIST = 0x01
        const val TRAINING_TIME = 0x02
        const val TIME_2_HEART = 0x04
        const val TIME_3_HEART = 0x08
        const val TIME_5_HEART = 0x10
        const val IND_BIKE_SIM = 0x20
        const val WHEEL_CONFIG = 0x40
        const val SPIN_DOWN = 0x80

    }
}
