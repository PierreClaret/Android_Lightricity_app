package com.lightricity.station.image.di

import com.lightricity.station.image.ImageInteractor
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

object ImageInjectionModule {
    val module = Kodein.Module(ImageInjectionModule.javaClass.name) {
        bind<ImageInteractor>() with singleton { ImageInteractor(instance()) }
    }
}