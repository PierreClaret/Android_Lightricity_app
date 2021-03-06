package com.lightricity.station.feature.provider

import android.content.Context
import android.content.SharedPreferences
import com.lightricity.station.feature.data.Feature

class RuntimeFeatureFlagProvider : FeatureFlagProvider {

    private val preferences: SharedPreferences

    override val priority = MAX_PRIORITY

    constructor(applicationContext: Context) {
        preferences = applicationContext.getSharedPreferences("runtime.featureflags", Context.MODE_PRIVATE)
    }

    override fun isFeatureEnabled(feature: Feature): Boolean =
        preferences.getBoolean(feature.key, feature.defaultValue)

    override fun hasFeature(feature: Feature): Boolean =
        preferences.contains(feature.key)

    fun setFeatureEnabled(feature: Feature, enabled: Boolean) =
        preferences.edit().putBoolean(feature.key, enabled).apply()
}