package com.lightricity.station.feature.data

interface Feature {
    val key: String
    val title: String
    val description: String
    val defaultValue: Boolean
}