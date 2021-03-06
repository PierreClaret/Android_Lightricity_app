package com.lightricity.station.network.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lightricity.station.R
import com.lightricity.station.network.domain.RuuviNetworkInteractor
import com.lightricity.station.tagdetails.ui.TagDetailsActivity
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance

class SignInActivity: AppCompatActivity(), KodeinAware{

    override val kodein: Kodein by closestKodein()
    val networkInteractor: RuuviNetworkInteractor by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onBackPressed() {
        if (networkInteractor.signedIn) {
            val parentIntent = Intent(this, TagDetailsActivity::class.java)
            parentIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(parentIntent)
            finish()
        } else {
            super.onBackPressed()
        }
    }

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, SignInActivity::class.java)
            context.startActivity(intent)
        }
    }
}