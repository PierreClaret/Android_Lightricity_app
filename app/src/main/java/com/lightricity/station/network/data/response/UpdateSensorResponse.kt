package com.lightricity.station.network.data.response

typealias UpdateSensorResponse = RuuviNetworkResponse<UpdateSensorResponseBody>

class UpdateSensorResponseBody (
    val sensor: String,
    val name: String
)