package com.lightricity.station.settings.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lightricity.station.feature.data.FeatureFlag
import com.lightricity.station.feature.provider.RuntimeFeatureFlagProvider

class AppSettingsExperimentalViewModel(
    private val runtimeFeatureFlagProvider: RuntimeFeatureFlagProvider
): ViewModel() {

    private val networkFeature = FeatureFlag.RUUVI_NETWORK

    private val ruuviNetworkEnabled = MutableLiveData<Boolean>(runtimeFeatureFlagProvider.isFeatureEnabled(networkFeature))
    val ruuviNetworkEnabledObserve: LiveData<Boolean> = ruuviNetworkEnabled

    fun setRuuviNetworkEnabled(checked: Boolean) {
        runtimeFeatureFlagProvider.setFeatureEnabled(networkFeature, checked)
    }
}