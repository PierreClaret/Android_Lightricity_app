package com.lightricity.station.gateway.di

import com.lightricity.station.gateway.GatewaySender
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

object GatewayInjectionModule {
    val module = Kodein.Module(GatewayInjectionModule.javaClass.name) {
        bind<GatewaySender>() with singleton { GatewaySender(instance(), instance()) }
    }
}