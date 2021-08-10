package com.lightricity.station.units.domain

import android.content.Context
import com.lightricity.station.R
import com.lightricity.station.app.preferences.PreferencesRepository
import com.lightricity.station.units.model.*
import com.lightricity.station.util.Utils
import java.lang.Math.abs

class UnitsConverter (
        private val context: Context,
        private val preferences: PreferencesRepository
) {

    // Temperature

    fun getTemperatureUnit(): TemperatureUnit = preferences.getTemperatureUnit()

    fun getAllTemperatureUnits(): Array<TemperatureUnit> = TemperatureUnit.values()

    fun getTemperatureUnitString(): String = context.getString(getTemperatureUnit().unit)

    fun getTemperatureValue(temperatureCelsius: Double): Double {
        return when (getTemperatureUnit()) {
            TemperatureUnit.CELSIUS -> temperatureCelsius
            TemperatureUnit.KELVIN-> TemperatureConverter.celsiusToKelvin(temperatureCelsius)
            TemperatureUnit.FAHRENHEIT -> TemperatureConverter.celsiusToFahrenheit(temperatureCelsius)
        }
    }

    fun getTemperatureString(temperature: Double?): String =
        if (temperature == 0.0) {
            NO_VALUE_AVAILABLE
        } else {
            context.getString(R.string.temperature_reading,
                temperature?.let { getTemperatureValue(it) }, getTemperatureUnitString())
        }

    fun getTemperatureStringWithoutUnit(temperature: Double?): String =
        if (temperature == 0.0) {
            NO_VALUE_AVAILABLE
        } else {
            context.getString(R.string.temperature_reading,
                temperature?.let { getTemperatureValue(it) }, "")
        }

    // Pressure

    fun getPressureUnit(): PressureUnit = preferences.getPressureUnit()

    fun getAllPressureUnits(): Array<PressureUnit> = PressureUnit.values()

    fun getPressureUnitString(): String = context.getString(getPressureUnit().unit)

    fun getPressureValue(pressurePascal: Double): Double {
        return when (getPressureUnit()) {
            PressureUnit.PA -> pressurePascal
            PressureUnit.HPA-> PressureConverter.pascalToHectopascal(pressurePascal)
            PressureUnit.MMHG -> PressureConverter.pascalToMmMercury(pressurePascal)
            PressureUnit.INHG -> PressureConverter.pascalToInchMercury(pressurePascal)
        }
    }

    fun getPressureString(pressure: Double?): String {
        return if (pressure == 0.0) {
            NO_VALUE_AVAILABLE
        } else {
            if (getPressureUnit() == PressureUnit.PA) {
                context.getString(R.string.pressure_reading_pa,
                    pressure?.let { getPressureValue(it) }, getPressureUnitString())
            } else {
                context.getString(R.string.pressure_reading,
                    pressure?.let { getPressureValue(it) }, getPressureUnitString())
            }
        }
    }

    // Humidity

    fun getHumidityUnit(): HumidityUnit = preferences.getHumidityUnit()

    fun getAllHumidityUnits(): Array<HumidityUnit> = HumidityUnit.values()

    fun getHumidityUnitString(): String = context.getString(getHumidityUnit().unit)

    fun getHumidityValue(humidity: Double, temperature: Double): Double {
        val converter = HumidityConverter(temperature, humidity/100)

        return when (getHumidityUnit()) {
            HumidityUnit.PERCENT -> Utils.round(humidity, 2)
            HumidityUnit.GM3-> Utils.round(converter.ah, 2)
            HumidityUnit.DEW -> {
                when (getTemperatureUnit()) {
                    TemperatureUnit.CELSIUS -> Utils.round(converter.Td ?: 0.0, 2)
                    TemperatureUnit.KELVIN-> Utils.round(converter.TdK ?: 0.0, 2)
                    TemperatureUnit.FAHRENHEIT -> Utils.round(converter.TdF ?: 0.0, 2)
                }
            }
        }
    }

    fun getHumidityString(humidity: Double?, temperature: Double?): String {
        return if (humidity == 0.0 || temperature == 0.0) {
            NO_VALUE_AVAILABLE
        } else {
            if (getHumidityUnit() == HumidityUnit.DEW) {
                context.getString(R.string.humidity_reading,
                    humidity?.let { temperature?.let { it1 -> getHumidityValue(it, it1) } }, getTemperatureUnitString())
            } else {
                context.getString(R.string.humidity_reading,
                    humidity?.let { temperature?.let { it1 -> getHumidityValue(it, it1) } }, getHumidityUnitString())
            }
        }
    }

    fun getSignalString(rssi: Int): String =
        if (rssi != 0) {
            context.getString(R.string.signal_reading, rssi, context.getString(R.string.signal_unit))
        } else {
            context.getString(R.string.signal_reading_zero, context.getString(R.string.signal_unit))
        }



    companion object {
        const val NO_VALUE_AVAILABLE = "-"
    }

    // Light
    fun getLightUnit(): LightUnit = preferences.getLightUnit()

    fun getLightUnitString(): String = context.getString(getLightUnit().unit)

    fun getLightValue(light: Double): Double {
        return when (getLightUnit()) {
            LightUnit.LUMEX -> light
        }
    }

    fun getLightString(light: Double?): String =
    if (light == 0.0) {
            NO_VALUE_AVAILABLE
        } else {
            context.getString(R.string.light_reading,
                light?.let { getLightValue(it) }, getLightUnitString())
        }


    //SOUND

    fun getSoundUnit(): SoundUnit = preferences.getSoundUnit()

    fun getSoundString(sound: Double?): String =
        if (sound == 0.0) {
            NO_VALUE_AVAILABLE
        } else {
            context.getString(R.string.sound_reading,
                sound?.let { getSoundValue(it) }, getSoundUnitString())
        }

    fun getSoundUnitString(): String = context.getString(getSoundUnit().unit)


    fun getSoundValue(sound: Double): Double {
        return when (getSoundUnit()) {
            SoundUnit.DB -> sound
        }
    }

    //Acceleration

    fun getMovementString(accelX:Double?, accelY: Double?, accelZ: Double?): String {
        val test = (accelX?.plus(accelY!!)?.plus(accelZ!!))
        return if (test?.let { abs(it) }!! >= 1.11) {
            context.getString(R.string.Mooving_state)
        } else if (test?.let { abs(it) } == 0.0){
            NO_VALUE_AVAILABLE
        }else{
            context.getString(R.string.Stable_state)
        }
    }

    //Magnetic

    fun getMagneticString(magX:Double?, magY: Double?, magZ: Double?): String {
        val test = (magX?.plus(magY!!)?.plus(magZ!!))
            return if (test!! >= 1.11) {
                context.getString(R.string.magnetic_state)
            }else if (test!! == 0.0) {
                NO_VALUE_AVAILABLE
            }else{
                context.getString(R.string.non_magnetcic_state)
        }
    }


}