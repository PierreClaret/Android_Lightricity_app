package com.lightricity.station.startup.di

import com.lightricity.station.startup.domain.StartupActivityInteractor
import com.lightricity.station.startup.ui.StartupActivityViewModel
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

object StartupActivityInjectionModule {
    val module = Kodein.Module(StartupActivityInjectionModule.javaClass.name) {
        bind<StartupActivityViewModel>() with provider {
            StartupActivityViewModel(instance(), instance(), instance())
        }

        bind<StartupActivityInteractor>() with singleton {
            StartupActivityInteractor(instance())
        }
    }
}