package com.lightricity.station.network.data.response

typealias UserRegisterResponse = RuuviNetworkResponse<UserRegisterResponseBody>

data class UserRegisterResponseBody (
    val email: String
)