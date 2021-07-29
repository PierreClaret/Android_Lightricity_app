package com.lightricity.station.tagdetails.domain

data class TagDetailsArguments(
    val desiredTag: String? = null,
    val shouldOpenAddView: Boolean = false
)