package com.lightricity.station.startup.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lightricity.station.util.extensions.viewModel
import com.lightricity.station.R
import com.lightricity.station.dashboard.ui.DashboardActivity
import com.lightricity.station.welcome.ui.WelcomeActivity
import com.lightricity.station.welcome.ui.WelcomeActivity.Companion.ARGUMENT_FROM_WELCOME
import com.lightricity.station.firebase.domain.FirebasePropertiesSaver
import com.lightricity.station.tagdetails.ui.TagDetailsActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance

@ExperimentalCoroutinesApi
class StartupActivity : AppCompatActivity(), KodeinAware {

    override val kodein by closestKodein()

    private val viewModel: StartupActivityViewModel by viewModel()
    private val firebasePropertySaver: FirebasePropertiesSaver by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_startup)

        firebasePropertySaver.saveUserProperties()

        viewModel.generateDeviceId()
        viewModel.startForegroundScanning()

        when {
            viewModel.isFirstStart() -> WelcomeActivity.start(this)
            viewModel.isDashboardEnabled() -> DashboardActivity.start(this)
            else -> {
                val isFromWelcome = intent.getBooleanExtra(ARGUMENT_FROM_WELCOME, false)
                TagDetailsActivity.start(this, isFromWelcome)
            }
        }
    }

    companion object {
        fun createIntentForNotification(context: Context): Intent =
            Intent(context, StartupActivity::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)

        fun start(context: Context, isFromWelcome: Boolean) {
            val intent = Intent(context, StartupActivity::class.java)
            intent.putExtra(ARGUMENT_FROM_WELCOME, isFromWelcome)
            context.startActivity(intent)
        }
    }
}
