package com.lightricity.station.app.di

import com.lightricity.station.app.preferences.Preferences
import com.lightricity.station.app.preferences.PreferencesRepository
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

object PreferencesInjectionModule {
    val module = Kodein.Module(PreferencesInjectionModule.javaClass.name) {
        bind<Preferences>() with singleton { Preferences(instance()) }

        bind<PreferencesRepository>() with singleton {
            PreferencesRepository(instance())
        }
    }
}