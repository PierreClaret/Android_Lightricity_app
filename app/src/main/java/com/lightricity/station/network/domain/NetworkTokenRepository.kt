package com.lightricity.station.network.domain

import com.lightricity.station.app.preferences.PreferencesRepository
import com.lightricity.station.network.data.NetworkTokenInfo

class NetworkTokenRepository (
    private val preferencesRepository: PreferencesRepository
) {
    fun saveTokenInfo(tokenInfo: NetworkTokenInfo) {
        preferencesRepository.setNetworkTokenInfo(tokenInfo)
    }

    fun getTokenInfo(): NetworkTokenInfo? = preferencesRepository.getNetworkTokenInfo()

    fun signOut() {
        saveTokenInfo(NetworkTokenInfo("", ""))
    }
}