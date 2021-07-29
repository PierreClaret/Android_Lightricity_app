package com.lightricity.station.tag.domain

import android.content.Context
import com.lightricity.station.app.preferences.Preferences
import com.lightricity.station.database.tables.RuuviTagEntity
import com.lightricity.station.units.domain.UnitsConverter

class TagConverter(
    private val context: Context,
    private val preferences: Preferences,
    private val unitsConverter: UnitsConverter
) {

    fun fromDatabase(entity: RuuviTagEntity): Sensor =
        Sensor(
            id = entity.id.orEmpty(),
            name = entity.name.orEmpty(),
            displayName = entity.name ?: entity.id.toString(),
            rssi = entity.rssi,
            temperature = entity.temperature,
            humidity = entity.humidity,
            pressure = entity.pressure,
            light = entity.light,
            sound = entity.sound,
            accelX = entity.accelX,
            accelY = entity.accelY,
            accelZ = entity.accelZ,
            magX = entity.magX,
            magY = entity.magY,
            magZ = entity.magZ,
            temperatureString = unitsConverter.getTemperatureString(entity.temperature),
            humidityString = unitsConverter.getHumidityString(entity.humidity, entity.temperature),
            pressureString = unitsConverter.getPressureString(entity.pressure),
            lightString = unitsConverter.getLightString(entity.light),
            soundString = unitsConverter.getSoundString(entity.sound),
            defaultBackground = entity.defaultBackground,
            dataFormat = entity.dataFormat,
            updatedAt = entity.updateAt,
            userBackground = entity.userBackground,
            connectable = entity.connectable,
            lastSync = entity.lastSync
        )
}