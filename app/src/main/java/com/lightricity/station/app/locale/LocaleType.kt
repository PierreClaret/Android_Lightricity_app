package com.lightricity.station.app.locale

import com.lightricity.station.R

enum class LocaleType (val code: String, val title: Int) {
    ENGLISH("en", R.string.language_english),
    FINNISH("fi", R.string.language_finnish),
    SWEDISH("sv", R.string.language_swedish),
    RUSSIAN("ru", R.string.language_russian)
}