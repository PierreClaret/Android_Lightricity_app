package com.lightricity.station.units.model

import com.lightricity.station.R

enum class CO2Unit (val code: Int, val title: Int, val unit: Int) {
    ppm(0, R.string.co2_ppm_name, R.string.co2_ppm_unit),
}