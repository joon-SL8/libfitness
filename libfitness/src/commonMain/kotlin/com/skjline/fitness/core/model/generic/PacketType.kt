package com.skjline.fitness.core.model.generic

enum class PacketType(val unit: String = Const.EMPTY) {
    Cadence(unit = "rpm"),
    Speed(unit = "mph"),
    HRData(unit = "bpm"),
    TotalTime,
    Interval,
    Power(unit = "w"),
    TargetPower(unit = "w"),
    TargetHR,
    Battery,
    Unknown,
}