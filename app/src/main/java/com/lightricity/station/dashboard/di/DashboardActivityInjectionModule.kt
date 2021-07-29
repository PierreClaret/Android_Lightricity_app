package com.lightricity.station.dashboard.di

import com.lightricity.station.dashboard.ui.DashboardActivityViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider

@ExperimentalCoroutinesApi
object DashboardActivityInjectionModule {

    val module = Kodein.Module(DashboardActivityInjectionModule.javaClass.name) {

        bind<DashboardActivityViewModel>() with provider {
            DashboardActivityViewModel(instance(), instance(), instance(), instance(), instance())
        }
    }
}