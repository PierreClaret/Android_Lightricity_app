package com.lightricity.station.settings.ui

import androidx.lifecycle.ViewModel
import com.lightricity.station.app.locale.LocaleType
import com.lightricity.station.app.preferences.PreferencesRepository
import com.lightricity.station.settings.domain.AppSettingsInteractor

class AppSettingsLocaleViewModel(
    private val appSettingsInteractor: AppSettingsInteractor,
    private val preferencesRepository: PreferencesRepository
) : ViewModel() {
    fun getAllTLocales():Array<LocaleType> = appSettingsInteractor.getAllLocales()

    fun getLocale() = preferencesRepository.getLocale()

    fun setLocale(locale: String) {
        preferencesRepository.setLocale(locale)
    }
}