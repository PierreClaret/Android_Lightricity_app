package com.lightricity.station.units.di

import com.lightricity.station.units.domain.UnitsConverter
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

object UnitsInjectionModule {
    val module = Kodein.Module(UnitsInjectionModule.javaClass.name) {

        bind<UnitsConverter>() with singleton {
            UnitsConverter(instance(), instance())
        }
    }
}