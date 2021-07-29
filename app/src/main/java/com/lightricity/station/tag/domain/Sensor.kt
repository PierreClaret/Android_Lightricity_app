package com.lightricity.station.tag.domain

import com.lightricity.station.alarm.domain.AlarmStatus
import java.util.Date

data class Sensor(
    val id: String,
    val name: String,
    val displayName: String,
    val rssi: Int,
    val temperature: Double?,
    val humidity: Double?,
    val pressure: Double?,
    val light: Double?,
    val sound: Double?,
    val accelX: Double?,
    val accelY: Double?,
    val accelZ: Double?,
    val magX: Double?,
    val magY: Double?,
    val magZ: Double?,
    val temperatureString: String,
    val humidityString: String,
    val pressureString: String,
    val lightString: String,
    val soundString: String,
    val defaultBackground: Int,
    val dataFormat: String,
    val updatedAt: Date?,
    val userBackground: String?,
    val status: AlarmStatus = AlarmStatus.NO_ALARM,
    val connectable: Boolean?,
    val lastSync: Date?
)