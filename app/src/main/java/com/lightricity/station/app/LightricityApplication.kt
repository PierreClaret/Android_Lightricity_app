package com.lightricity.station.app

import android.app.Application
import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.content.IntentFilter
import android.os.PowerManager
import com.facebook.stetho.Stetho
import com.raizlabs.android.dbflow.config.FlowManager
import com.lightricity.station.BuildConfig
import com.lightricity.station.app.di.AppInjectionModules
import com.lightricity.station.app.preferences.PreferencesRepository
import com.lightricity.station.bluetooth.DefaultOnTagFoundListener
import com.lightricity.station.bluetooth.domain.BluetoothStateReceiver
import com.lightricity.station.feature.data.FeatureFlag
import com.lightricity.station.feature.domain.RuntimeBehavior
import com.lightricity.station.feature.provider.RuntimeFeatureFlagProvider
import com.lightricity.station.network.domain.NetworkDataSyncInteractor
import com.lightricity.station.network.domain.RuuviNetworkInteractor
import com.lightricity.station.util.Foreground
import com.lightricity.station.util.ForegroundListener
import com.lightricity.station.util.ReleaseTree
import com.lightricity.station.util.extensions.diffGreaterThan
import com.lightricity.station.util.test.FakeScanResultsSender
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.conf.ConfigurableKodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import timber.log.Timber
import java.util.*

@ExperimentalCoroutinesApi
class LightricityApplication : Application(), KodeinAware {
    override val kodein = ConfigurableKodein()

    val defaultOnTagFoundListener: DefaultOnTagFoundListener by instance()
    private val fakesSender: FakeScanResultsSender by instance()
    private val bluetoothReceiver: BluetoothStateReceiver by instance()
    private val foreground: Foreground by instance()
    private val networkInteractor: RuuviNetworkInteractor by instance()
    private val networkDataSyncInteractor: NetworkDataSyncInteractor by instance()
    private val preferencesRepository: PreferencesRepository by instance()
    private val runtimeFeatureFlagProvider: RuntimeFeatureFlagProvider by instance()
    private val runtimeBehavior: RuntimeBehavior by instance()

    private var isInForeground: Boolean = false

    private val listener: ForegroundListener = object : ForegroundListener {
        override fun onBecameForeground() {
            isInForeground = true
            defaultOnTagFoundListener.isForeground = true
            updateNetwork()
            runtimeBehavior.refreshFeatureFlags()
        }

        override fun onBecameBackground() {
            isInForeground = false
            defaultOnTagFoundListener.isForeground = false
        }
    }

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else if (BuildConfig.FILE_LOGS_ENABLED) {
            Timber.plant(ReleaseTree())
        }

        setupDependencyInjection()

        //Lingver.init(this, Locale(preferencesRepository.getLocale()))

        FlowManager.init(this)

        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this)
            //turn on for debug if you don't have real lightricity tag
            //fakesSender.startSendFakes()
        }

        registerReceiver(bluetoothReceiver, IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED))

        defaultOnTagFoundListener.isForeground = isInForeground
        foreground.addListener(listener)

        setupExperimentalFeatures()

        updateNetwork()
    }

    private fun setupDependencyInjection() {
        kodein.apply {
            addImport(AppInjectionModules.module)

            addImport(Kodein.Module(javaClass.name) {
                bind<Application>() with singleton { this@LightricityApplication }
                bind<PowerManager>() with singleton {
                    this@LightricityApplication.getSystemService(Context.POWER_SERVICE) as PowerManager
                }
            })
        }
    }

    private fun setupExperimentalFeatures() {
        if (networkInteractor.signedIn) {
            runtimeFeatureFlagProvider.setFeatureEnabled(FeatureFlag.RUUVI_NETWORK, true)
        }
    }

    private fun updateNetwork() {
        if (networkInteractor.signedIn && Date(preferencesRepository.getLastSyncDate()).diffGreaterThan(60*1000)) {
            networkDataSyncInteractor.syncNetworkData()
        }
    }
}