package com.lightricity.station.tag.domain

import com.lightricity.station.alarm.domain.AlarmStatus
import java.util.Date

data class Sensor(
    val id: String,
    val name: String,
    val frametype: Int? = null,
    val sensorid: Int? = null,
    val vandorid: Int? = null,
    val displayName: String,
    val rssi: Int,
    val temperature: Double? = null,
    val humidity: Double? = null,
    val pressure: Double? = null,
    val light: Double? = null,
    val sound: Double? = null,
    val co2: Double? = null,
    val accelX: Double? = null,
    val accelY: Double? = null,
    val accelZ: Double? = null,
    val magX: Double? = null,
    val magY: Double? = null,
    val magZ: Double? = null,
    val temperatureString: String? = null,
    val humidityString: String? = null,
    val pressureString: String? = null,
    val lightString: String? = null,
    val soundString: String? = null,
    val defaultBackground: Int,
    val dataFormat: String,
    val updatedAt: Date?,
    val userBackground: String?,
    val status: AlarmStatus = AlarmStatus.NO_ALARM,
    var connectable: Boolean?,
    val lastSync: Date?
)