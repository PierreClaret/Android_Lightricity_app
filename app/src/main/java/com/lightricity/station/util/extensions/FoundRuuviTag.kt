package com.lightricity.station.util.extensions

import com.lightricity.station.bluetooth.FoundSensor

fun FoundSensor.logData(): String {
    return "tag[$id]: dataFormat = $dataFormat; temp = $temperature; humidity = $humidity; pressure = $pressure"
}