package com.lightricity.station.settings.ui

import androidx.lifecycle.ViewModel
import com.lightricity.station.settings.domain.AppSettingsInteractor
import com.lightricity.station.units.model.PressureUnit

class AppSettingsPressureUnitViewModel (private val appSettingsInteractor: AppSettingsInteractor) : ViewModel() {

    fun getAllPressureUnits():Array<PressureUnit> = appSettingsInteractor.getAllPressureUnits()

    fun getPressureUnit(): PressureUnit = appSettingsInteractor.getPressureUnit()

    fun setPressureUnit(unit: PressureUnit) {
        appSettingsInteractor.setPressureUnit(unit)
    }
}