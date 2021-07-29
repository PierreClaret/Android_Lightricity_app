package com.lightricity.station.app.di

import com.lightricity.station.about.di.AboutActivityInjectionModule
import com.lightricity.station.addtag.di.AddTagActivityInjectionModule
import com.lightricity.station.alarm.di.AlarmModule
import com.lightricity.station.bluetooth.di.BluetoothScannerInjectionModule
import com.lightricity.station.dashboard.di.DashboardActivityInjectionModule
import com.lightricity.station.feature.di.FeatureInjectionModule
import com.lightricity.station.firebase.di.FirebaseInjectionModule
import com.lightricity.station.gateway.di.GatewayInjectionModule
import com.lightricity.station.graph.di.GraphInjectionModule
import com.lightricity.station.image.di.ImageInjectionModule
import com.lightricity.station.network.di.NetworkInjectionModule
import com.lightricity.station.settings.di.SettingsInjectionModule
import com.lightricity.station.startup.di.StartupActivityInjectionModule
import com.lightricity.station.tag.di.RuuviTagInjectionModule
import com.lightricity.station.tagdetails.di.TagDetailsInjectionModule
import com.lightricity.station.tagsettings.di.TagSettingsInjectionModule
import com.lightricity.station.units.di.UnitsInjectionModule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.kodein.di.Kodein

@ExperimentalCoroutinesApi
object AppInjectionModules {
    val module = Kodein.Module(AppInjectionModules.javaClass.name) {
        import(AppInjectionModule.module)
        import(FirebaseInjectionModule.module)
        import(PreferencesInjectionModule.module)
        import(BluetoothScannerInjectionModule.module)
        import(SettingsInjectionModule.module)
        import(GatewayInjectionModule.module)
        import(TagDetailsInjectionModule.module)
        import(DashboardActivityInjectionModule.module)
        import(StartupActivityInjectionModule.module)
        import(AboutActivityInjectionModule.module)
        import(AddTagActivityInjectionModule.module)
        import(TagSettingsInjectionModule.module)
        import(RuuviTagInjectionModule.module)
        import(AlarmModule.module)
        import(UnitsInjectionModule.module)
        import(GraphInjectionModule.module)
        import(NetworkInjectionModule.module)
        import(ImageInjectionModule.module)
        import(FeatureInjectionModule.module)
    }
}