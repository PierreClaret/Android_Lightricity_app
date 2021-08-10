package com.lightricity.station.tagdetails.domain

import com.lightricity.station.database.TagRepository
import com.lightricity.station.database.tables.TagSensorReading
import com.lightricity.station.tag.domain.Sensor
import com.lightricity.station.tag.domain.TagConverter
import com.lightricity.station.units.domain.UnitsConverter
import java.util.*

class TagDetailsInteractor(
    private val tagRepository: TagRepository,
    private val tagConverter: TagConverter,
    private val unitsConverter: UnitsConverter

) {

    fun getTagById(tagId: String): Sensor? =
        tagRepository.getTagById(tagId)?.let { tagConverter.fromDatabase(it) }

    fun updateLastSync(tagId: String, date: Date?): Sensor? =
        tagRepository.getTagById(tagId)?.let {
            it.lastSync = date
            it.update()
            return tagConverter.fromDatabase(it)
        }

    fun clearLastSync(tagId: String): Sensor? =
        tagRepository.getTagById(tagId)?.let {
            it.lastSync = null
            it.networkLastSync = null
            it.update()
            return tagConverter.fromDatabase(it)
        }

    fun getTagReadings(tagId: String): List<TagSensorReading>? =
        tagRepository.getTagReadings(tagId)

    fun getTemperatureString(tag: Sensor): String =
        unitsConverter.getTemperatureString(tag.temperature)

    fun getTemperatureStringWithoutUnit(tag: Sensor): String =
        unitsConverter.getTemperatureStringWithoutUnit(tag.temperature)

    fun getTemperatureUnitString(): String =
        unitsConverter.getTemperatureUnitString()

    fun getHumidityString(tag: Sensor): String =
        unitsConverter.getHumidityString(tag.humidity, tag.temperature)

    fun getPressureString(tag: Sensor): String =
        unitsConverter.getPressureString(tag.pressure)

    fun getSignalString(tag: Sensor): String =
        unitsConverter.getSignalString(tag.rssi)

    fun getLightString(tag: Sensor): String =
        unitsConverter.getLightString(tag.light)

    fun getSoundString(tag: Sensor): String =
        unitsConverter.getSoundString(tag.sound)

    fun getMovementString(tag: Sensor): String =
        unitsConverter.getMovementString(tag.accelX,tag.accelY,tag.accelZ)

    fun getMagneticString(tag: Sensor): String =
        unitsConverter.getMagneticString(tag.magX, tag.magY, tag.magZ)


}