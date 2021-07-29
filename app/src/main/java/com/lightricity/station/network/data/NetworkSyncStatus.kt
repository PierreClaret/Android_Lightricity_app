package com.lightricity.station.network.data

data class NetworkSyncStatus (
    val syncInProgress: Boolean,
    val lastSync: Long
)