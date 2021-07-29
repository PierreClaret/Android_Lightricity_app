package com.lightricity.station.network.data.response

typealias UserVerifyResponse = RuuviNetworkResponse<UserVerifyResponseBody>

data class UserVerifyResponseBody (
    val email: String,
    val accessToken: String,
    val newUser: Boolean
)