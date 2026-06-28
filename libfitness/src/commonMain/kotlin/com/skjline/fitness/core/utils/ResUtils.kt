package com.skjline.fitness.core.utils

import com.skjline.fitness.core.model.generic.Const.Companion.EMPTY
import com.skjline.fitness.resources.FTP
import com.skjline.fitness.resources.IF
import com.skjline.fitness.resources.NP
import com.skjline.fitness.resources.Res
import com.skjline.fitness.resources.TSS
import com.skjline.fitness.resources.action_activity
import com.skjline.fitness.resources.action_home
import com.skjline.fitness.resources.action_profile
import com.skjline.fitness.resources.action_training
import com.skjline.fitness.resources.activity
import com.skjline.fitness.resources.activity_fragment_label
import com.skjline.fitness.resources.aerobic
import com.skjline.fitness.resources.anaerobic
import com.skjline.fitness.resources.app_name
import com.skjline.fitness.resources.average
import com.skjline.fitness.resources.ble_device_selected
import com.skjline.fitness.resources.ble_devices_header
import com.skjline.fitness.resources.ble_devices_list
import com.skjline.fitness.resources.ble_devices_list_searching
import com.skjline.fitness.resources.cadence
import com.skjline.fitness.resources.calendar_fragment_label
import com.skjline.fitness.resources.chart_characteristic_label
import com.skjline.fitness.resources.chart_development_label
import com.skjline.fitness.resources.compose_multiplatform
import com.skjline.fitness.resources.distance
import com.skjline.fitness.resources.distance_imperial
import com.skjline.fitness.resources.distance_metric
import com.skjline.fitness.resources.duration
import com.skjline.fitness.resources.fatigue
import com.skjline.fitness.resources.fitness
import com.skjline.fitness.resources.fitness_header
import com.skjline.fitness.resources.form
import com.skjline.fitness.resources.heart_rate
import com.skjline.fitness.resources.home
import com.skjline.fitness.resources.home_fragment_label
import com.skjline.fitness.resources.hr
import com.skjline.fitness.resources.ic_account_profile
import com.skjline.fitness.resources.ic_bike
import com.skjline.fitness.resources.ic_folder
import com.skjline.fitness.resources.ic_home
import com.skjline.fitness.resources.ic_library
import com.skjline.fitness.resources.ic_person
import com.skjline.fitness.resources.icon_cad
import com.skjline.fitness.resources.icon_ctrl_kbd
import com.skjline.fitness.resources.icon_fms
import com.skjline.fitness.resources.icon_hr
import com.skjline.fitness.resources.icon_power
import com.skjline.fitness.resources.icon_sac
import com.skjline.fitness.resources.icon_spd
import com.skjline.fitness.resources.icon_sync
import com.skjline.fitness.resources.icon_temp
import com.skjline.fitness.resources.icon_track
import com.skjline.fitness.resources.imperial
import com.skjline.fitness.resources.interval
import com.skjline.fitness.resources.max
import com.skjline.fitness.resources.metric
import com.skjline.fitness.resources.min
import com.skjline.fitness.resources.neuromuscular
import com.skjline.fitness.resources.pause
import com.skjline.fitness.resources.power
import com.skjline.fitness.resources.profile_fragment_label
import com.skjline.fitness.resources.recovery
import com.skjline.fitness.resources.session_title
import com.skjline.fitness.resources.speed
import com.skjline.fitness.resources.start
import com.skjline.fitness.resources.stop
import com.skjline.fitness.resources.target_power
import com.skjline.fitness.resources.tempo
import com.skjline.fitness.resources.threshold
import com.skjline.fitness.resources.title_activity_session
import com.skjline.fitness.resources.today
import com.skjline.fitness.resources.todays_training
import com.skjline.fitness.resources.todays_training_start
import com.skjline.fitness.resources.total_time
import com.skjline.fitness.resources.train_plan_fragment_label
import com.skjline.fitness.resources.trainee_name
import com.skjline.fitness.resources.trainer
import com.skjline.fitness.resources.unit_bpm
import com.skjline.fitness.resources.unit_kpw
import com.skjline.fitness.resources.unit_min
import com.skjline.fitness.resources.unit_minute
import com.skjline.fitness.resources.unit_rpm
import com.skjline.fitness.resources.unit_sec
import com.skjline.fitness.resources.unit_second
import com.skjline.fitness.resources.unit_watt
import com.skjline.fitness.resources.view_graph
import com.skjline.fitness.resources.vo2max
import com.skjline.fitness.resources.weight_imperial
import com.skjline.fitness.resources.weight_metric
import com.skjline.fitness.resources.zone
import com.skjline.fitness.resources.zone_x
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.InternalResourceApi
import org.jetbrains.compose.resources.StringResource

@OptIn(InternalResourceApi::class)
fun String.toStringRes(): StringResource {
    return when (this) {
        "FTP" -> Res.string.FTP
        "IF" -> Res.string.IF
        "NP" -> Res.string.NP
        "TSS" -> Res.string.TSS
        "action_activity" -> Res.string.action_activity
        "action_home" -> Res.string.action_home
        "action_profile" -> Res.string.action_profile
        "action_training" -> Res.string.action_training
        "activity" -> Res.string.activity
        "activity_fragment_label" -> Res.string.activity_fragment_label
        "aerobic" -> Res.string.aerobic
        "anaerobic" -> Res.string.anaerobic
        "app_name" -> Res.string.app_name
        "average" -> Res.string.average
        "ble_device_selected" -> Res.string.ble_device_selected
        "ble_devices_header" -> Res.string.ble_devices_header
        "ble_devices_list" -> Res.string.ble_devices_list
        "ble_devices_list_searching" -> Res.string.ble_devices_list_searching
        "cadence" -> Res.string.cadence
        "calendar_fragment_label" -> Res.string.calendar_fragment_label
        "chart_characteristic_label" -> Res.string.chart_characteristic_label
        "chart_development_label" -> Res.string.chart_development_label
        "distance" -> Res.string.distance
        "distance_imperial" -> Res.string.distance_imperial
        "distance_metric" -> Res.string.distance_metric
        "duration" -> Res.string.duration
        "fatigue" -> Res.string.fatigue
        "fitness" -> Res.string.fitness
        "fitness_header" -> Res.string.fitness_header
        "form" -> Res.string.form
        "heart_rate" -> Res.string.heart_rate
        "home" -> Res.string.home
        "home_fragment_label" -> Res.string.home_fragment_label
        "hr" -> Res.string.hr
        "imperial" -> Res.string.imperial
        "interval" -> Res.string.interval
        "max" -> Res.string.max
        "metric" -> Res.string.metric
        "min" -> Res.string.min
        "neuromuscular" -> Res.string.neuromuscular
        "pause" -> Res.string.pause
        "power" -> Res.string.power
        "profile_fragment_label" -> Res.string.profile_fragment_label
        "recovery" -> Res.string.recovery
        "session_title" -> Res.string.session_title
        "speed" -> Res.string.speed
        "start" -> Res.string.start
        "stop" -> Res.string.stop
        "target_power" -> Res.string.target_power
        "tempo" -> Res.string.tempo
        "threshold" -> Res.string.threshold
        "title_activity_session" -> Res.string.title_activity_session
        "today" -> Res.string.today
        "todays_training" -> Res.string.todays_training
        "todays_training_start" -> Res.string.todays_training_start
        "total_time" -> Res.string.total_time
        "train_plan_fragment_label" -> Res.string.train_plan_fragment_label
        "trainee_name" -> Res.string.trainee_name
        "trainer" -> Res.string.trainer
        "unit_bpm" -> Res.string.unit_bpm
        "unit_kpw" -> Res.string.unit_kpw
        "unit_min" -> Res.string.unit_min
        "unit_minute" -> Res.string.unit_minute
        "unit_rpm" -> Res.string.unit_rpm
        "unit_sec" -> Res.string.unit_sec
        "unit_second" -> Res.string.unit_second
        "unit_watt" -> Res.string.unit_watt
        "view_graph" -> Res.string.view_graph
        "vo2max" -> Res.string.vo2max
        "weight_imperial" -> Res.string.weight_imperial
        "weight_metric" -> Res.string.weight_metric
        "zone" -> Res.string.zone
        "zone_x" -> Res.string.zone_x
        else -> StringResource(id = EMPTY, key = EMPTY, items = emptySet())
    }
}

fun String.toDrawableRes(): DrawableResource {
    return when (this) {
        "ic_account_profile" -> Res.drawable.ic_account_profile
        "ic_bike" -> Res.drawable.ic_bike
        "ic_folder" -> Res.drawable.ic_folder
        "ic_home" -> Res.drawable.ic_home
        "ic_library" -> Res.drawable.ic_library
        "ic_person" -> Res.drawable.ic_person
        "icon_cad" -> Res.drawable.icon_cad
        "icon_ctrl_kbd" -> Res.drawable.icon_ctrl_kbd
        "icon_fms" -> Res.drawable.icon_fms
        "icon_hr" -> Res.drawable.icon_hr
        "icon_power" -> Res.drawable.icon_power
        "icon_sac" -> Res.drawable.icon_sac
        "icon_spd" -> Res.drawable.icon_spd
        "icon_sync" -> Res.drawable.icon_sync
        "icon_temp" -> Res.drawable.icon_temp
        "icon_track" -> Res.drawable.icon_track
        else -> Res.drawable.compose_multiplatform
    }
}