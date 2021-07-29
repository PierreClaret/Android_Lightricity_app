package com.lightricity.station.settings.ui

import androidx.lifecycle.ViewModel
import com.lightricity.station.app.preferences.PreferencesRepository
import com.lightricity.station.units.model.HumidityUnit
import com.lightricity.station.settings.domain.AppSettingsInteractor
import com.lightricity.station.units.model.PressureUnit
import com.lightricity.station.units.model.TemperatureUnit
import com.lightricity.station.units.model.LightUnit
import com.lightricity.station.util.BackgroundScanModes

class AppSettingsListViewModel(
    private val interactor: AppSettingsInteractor,
    private val preferencesRepository: PreferencesRepository
) : ViewModel() {

    val experimentalFeatures = preferencesRepository.getExperimentalFeaturesLiveData()

    fun getBackgroundScanMode(): BackgroundScanModes =
        interactor.getBackgroundScanMode()

    fun isDashboardEnabled(): Boolean =
        interactor.isDashboardEnabled()

    fun setIsDashboardEnabled(isEnabled: Boolean) =
        interactor.setIsDashboardEnabled(isEnabled)

    fun getBackgroundScanInterval(): Int =
        interactor.getBackgroundScanInterval()

    fun getGatewayUrl(): String =
        interactor.getGatewayUrl()

    fun getTemperatureUnit(): TemperatureUnit =
        interactor.getTemperatureUnit()

    fun getHumidityUnit(): HumidityUnit =
        interactor.getHumidityUnit()

    fun getPressureUnit(): PressureUnit =
        interactor.getPressureUnit()

    fun getLightUnit(): LightUnit =
        interactor.getLightUnit()

}