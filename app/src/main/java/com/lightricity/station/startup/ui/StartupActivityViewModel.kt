package com.lightricity.station.startup.ui

import androidx.lifecycle.ViewModel
import com.lightricity.station.app.preferences.PreferencesRepository
import com.lightricity.station.bluetooth.BluetoothInteractor
import com.lightricity.station.startup.domain.StartupActivityInteractor

class StartupActivityViewModel(
    private val bluetoothInteractor: BluetoothInteractor,
    private val startupInteractor: StartupActivityInteractor,
    private val preferencesRepository: PreferencesRepository
) : ViewModel() {

    fun startForegroundScanning() {
        if (bluetoothInteractor.canScan())
            bluetoothInteractor.startForegroundScanning()
    }

    fun isFirstStart(): Boolean = startupInteractor.isFirstStart()

    fun isDashboardEnabled(): Boolean = startupInteractor.isDashboardEnabled()

    fun generateDeviceId() {
        preferencesRepository.getDeviceId()
    }
}