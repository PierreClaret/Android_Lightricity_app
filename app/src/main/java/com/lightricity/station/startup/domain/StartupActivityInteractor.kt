package com.lightricity.station.startup.domain

import com.lightricity.station.app.preferences.PreferencesRepository

class StartupActivityInteractor(private val preferencesRepository: PreferencesRepository) {

    fun isFirstStart(): Boolean = preferencesRepository.isFirstStart()

    fun isDashboardEnabled(): Boolean = preferencesRepository.isDashboardEnabled()
}