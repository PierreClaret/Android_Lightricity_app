package com.lightricity.station.graph

data class GraphEntry(
    val timestamp: Float,
    val temperature: Float,
    val humidity: Float?,
    val pressure: Float?,
    val light: Float?,
    val sound: Float?
)