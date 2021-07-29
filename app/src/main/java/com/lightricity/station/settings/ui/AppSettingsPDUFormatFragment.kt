package com.lightricity.station.settings.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lightricity.station.R

class AppSettingsPDUFormatFragment : Fragment() {

    companion object {
        fun newInstance() = AppSettingsPDUFormatFragment()
    }

    private lateinit var viewModel: AppSettingsPDUFormatViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.app_settings_pdu_format_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AppSettingsPDUFormatViewModel::class.java)
        // TODO: Use the ViewModel
    }

}