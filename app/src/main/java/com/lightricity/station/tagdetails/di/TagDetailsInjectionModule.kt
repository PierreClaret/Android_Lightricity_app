package com.lightricity.station.tagdetails.di

import com.lightricity.station.database.TagRepository
import com.lightricity.station.tagdetails.domain.TagDetailsArguments
import com.lightricity.station.tagdetails.domain.TagDetailsInteractor
import com.lightricity.station.tagdetails.domain.TagViewModelArgs
import com.lightricity.station.tagdetails.ui.TagDetailsViewModel
import com.lightricity.station.tagdetails.ui.TagViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.factory
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

@ExperimentalCoroutinesApi
object TagDetailsInjectionModule {
    val module = Kodein.Module(TagDetailsInjectionModule.javaClass.name) {

        bind<TagDetailsInteractor>() with singleton {
            TagDetailsInteractor(instance(), instance(), instance())
        }

        bind<TagDetailsViewModel>() with factory { args: TagDetailsArguments ->
            TagDetailsViewModel(args, instance(), instance(), instance(), instance(), instance())
        }

        bind<TagViewModel>() with factory { args: TagViewModelArgs ->
            TagViewModel(instance(), instance(), tagId = args.tagId)
        }

        bind<TagRepository>() with singleton {
            TagRepository(instance())
        }
    }
}