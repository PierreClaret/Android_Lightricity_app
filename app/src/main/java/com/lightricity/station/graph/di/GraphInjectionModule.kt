package com.lightricity.station.graph.di

import com.lightricity.station.graph.GraphView
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider

object GraphInjectionModule {
    val module = Kodein.Module(GraphInjectionModule.javaClass.name) {

        bind<GraphView>() with provider {
            GraphView(instance(), instance(), instance())
        }
    }
}
