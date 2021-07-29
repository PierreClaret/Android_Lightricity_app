package com.lightricity.station.dashboard.ui

import androidx.lifecycle.*
import com.lightricity.station.app.preferences.PreferencesRepository
import com.lightricity.station.network.data.NetworkSyncResult
import com.lightricity.station.network.data.NetworkSyncResultType
import com.lightricity.station.network.data.NetworkSyncStatus
import com.lightricity.station.network.domain.NetworkDataSyncInteractor
import com.lightricity.station.network.domain.NetworkTokenRepository
import com.lightricity.station.tag.domain.Sensor
import com.lightricity.station.tag.domain.TagInteractor
import com.lightricity.station.units.domain.UnitsConverter
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import timber.log.Timber

@ExperimentalCoroutinesApi
class DashboardActivityViewModel(
    private val tagInteractor: TagInteractor,
    val converter: UnitsConverter,
    val networkDataSyncInteractor: NetworkDataSyncInteractor,
    private val preferencesRepository: PreferencesRepository,
    private val tokenRepository: NetworkTokenRepository
) : ViewModel() {

    private val tags = MutableLiveData<List<Sensor>>(arrayListOf())
    val observeTags: LiveData<List<Sensor>> = tags

    private val syncResult = MutableLiveData<NetworkSyncResult>(NetworkSyncResult(NetworkSyncResultType.NONE))
    val syncResultObserve: LiveData<NetworkSyncResult> = syncResult

    private val syncInProgress = MutableLiveData<Boolean>(false)
    val syncInProgressObserve: LiveData<Boolean> = syncInProgress

    val userEmail = preferencesRepository.getUserEmailLiveData()

    val lastSync = preferencesRepository.getLastSyncDateLiveData()

    val syncStatus: MediatorLiveData<NetworkSyncStatus> = MediatorLiveData<NetworkSyncStatus>()

    private val trigger = MutableLiveData<Int>(1)

    init {
        updateTags()
        Timber.d("DashboardActivityViewModel-syncStatusFlow")

        viewModelScope.launch {
            networkDataSyncInteractor.syncResultFlow.collect {
                syncResult.value = it
            }
        }
        viewModelScope.launch {
            networkDataSyncInteractor.syncInProgressFlow.collect{
                syncInProgress.value = it
                updateTags()
            }
        }

        syncStatus.addSource(syncInProgress) { syncStatus.value = getSyncStatus() }
        syncStatus.addSource(lastSync) { syncStatus.value = getSyncStatus() }
        syncStatus.addSource(trigger) { syncStatus.value = getSyncStatus() }
    }

    private fun getSyncStatus(): NetworkSyncStatus = NetworkSyncStatus(
        syncInProgress.value ?: false,
        lastSync.value ?: Long.MIN_VALUE
    )

    fun updateTags() {
        viewModelScope.launch {
            val getTags = tagInteractor.getTags()
            withContext(Dispatchers.Main) {
                tags.value = getTags
            }
        }
    }

    fun networkDataSync() {
        networkDataSyncInteractor.syncNetworkData()
    }

    fun syncResultShowed() {
        networkDataSyncInteractor.syncStatusShowed()
    }

    fun updateNetworkStatus() {
        CoroutineScope(Dispatchers.Main).launch {
            trigger.value = -1
        }
    }

    fun signOut() {
        tokenRepository.signOut()
    }
}