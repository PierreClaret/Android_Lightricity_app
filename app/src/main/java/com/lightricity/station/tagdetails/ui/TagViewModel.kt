package com.lightricity.station.tagdetails.ui

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lightricity.station.app.preferences.GlobalSettings
import com.lightricity.station.bluetooth.IRuuviGattListener
import com.lightricity.station.bluetooth.LogReading
import com.lightricity.station.bluetooth.domain.BluetoothGattInteractor
import com.lightricity.station.database.tables.TagSensorReading
import com.lightricity.station.tag.domain.Sensor
import com.lightricity.station.tagdetails.domain.TagDetailsInteractor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.util.*


class TagViewModel(
        private val tagDetailsInteractor: TagDetailsInteractor,
        private val gattInteractor: BluetoothGattInteractor,
        val tagId: String
) : ViewModel() {
    private val tagEntry = MutableLiveData<Sensor?>(null)
    val tagEntryObserve: LiveData<Sensor?> = tagEntry

    private val tagReadings = MutableLiveData<List<TagSensorReading>?>(null)
    val tagReadingsObserve: LiveData<List<TagSensorReading>?> = tagReadings

    enum class SyncProgress {
        STILL, CONNECTING, CONNECTED, DISCONNECTED, READING_INFO, READING_DATA, SAVING_DATA, NOT_SUPPORTED, NOT_FOUND, ERROR, DONE
    }

    class SyncStatus {
        var syncProgress = SyncProgress.STILL
        var deviceInfoModel = ""
        var deviceInfoFw = ""
        var readDataSize = 0
    }

    private val syncStatusObj = MutableLiveData<SyncStatus>()
    val syncStatusObserve: LiveData<SyncStatus> = syncStatusObj

    private val ioScope = CoroutineScope(Dispatchers.IO)

    private var showGraph = false

    private var selected = false

    init {
        Timber.d("TagViewModel initialized")
        getTagInfo()
    }

    fun isShowGraph(isShow: Boolean) {
        showGraph = isShow
    }

    fun disconnectGatt() {
        tagEntryObserve.value?.let { tag ->
            gattInteractor.disconnect(tag.id)
        }
    }

    fun syncGatt() {
        syncStatusObj.value = SyncStatus()
        syncStatusObj.value?.syncProgress = SyncProgress.CONNECTING
        syncStatusObj.postValue(syncStatusObj.value)
        tagEntryObserve.value?.let { tag ->
            var syncFrom = tag.lastSync
            val historyLength = Date(Date().time - 1000 * 60 * 60 * 24 * GlobalSettings.historyLengthDays)
            if (syncFrom == null || syncFrom.before(historyLength)) {
                syncFrom = historyLength
            }
            Timber.d("sync logs from: %s", syncFrom)
            val found = gattInteractor.readLogs(tag.id, syncFrom, object : IRuuviGattListener {
                override fun connected(state: Boolean) {
                    if (state) {
                        syncStatusObj.value?.syncProgress = SyncProgress.CONNECTED
                        syncStatusObj.value?.syncProgress = SyncProgress.READING_INFO
                    } else {
                        if (syncStatusObj.value?.syncProgress == SyncProgress.SAVING_DATA) {
                            syncStatusObj.value?.syncProgress = SyncProgress.DONE
                        } else {
                            syncStatusObj.value?.syncProgress = SyncProgress.DISCONNECTED
                        }
                    }
                    syncStatusObj.postValue(syncStatusObj.value)
                }

                override fun deviceInfo(model: String, fw: String, canReadLogs: Boolean) {
                    syncStatusObj.value?.deviceInfoModel = model
                    syncStatusObj.value?.deviceInfoFw = fw
                    if (canReadLogs) {
                        syncStatusObj.value?.syncProgress = SyncProgress.READING_DATA
                    } else {
                        syncStatusObj.value?.syncProgress = SyncProgress.NOT_SUPPORTED
                    }
                    syncStatusObj.postValue(syncStatusObj.value)
                }

                override fun dataReady(data: List<LogReading>) {
                    syncStatusObj.value?.readDataSize = data.size
                    syncStatusObj.value?.syncProgress = SyncProgress.SAVING_DATA
                    syncStatusObj.postValue(syncStatusObj.value)
                    saveGattReadings(tag, data)
                }

                override fun heartbeat(raw: String) {
                }

                override fun syncProgress(syncedPoints: Int) {
                    TODO("Not yet implemented")
                }
            })
            if (!found) {
                syncStatusObj.value?.syncProgress = SyncProgress.NOT_FOUND
                syncStatusObj.postValue(syncStatusObj.value)
            }
        } ?: kotlin.run {
            Handler(Looper.getMainLooper()).post {
                syncStatusObj.value?.syncProgress = SyncProgress.ERROR
                syncStatusObj.postValue(syncStatusObj.value)
            }
        }
    }

    fun removeTagData() {
        TagSensorReading.removeForTag(tagId)
        tagDetailsInteractor.clearLastSync(tagId)
    }

    fun saveGattReadings(tag: Sensor, data: List<LogReading>) {
        val tagReadingList = mutableListOf<TagSensorReading>()
        data.forEach { logReading ->
            val reading = TagSensorReading()
            reading.ruuviTagId = tag.id
            reading.temperature = logReading.temperature
            reading.humidity = logReading.humidity
            reading.pressure = logReading.pressure
            reading.light = logReading.light
            reading.sound = logReading.sound

            reading.createdAt = logReading.date
            tagReadingList.add(reading)
        }
        TagSensorReading.saveList(tagReadingList)
        updateLastSync(Date())
    }

    fun updateLastSync(date: Date?) {
        tagDetailsInteractor.updateLastSync(tagId, date)
    }

    fun tagSelected(selectedTag: Sensor?) {
        selected = tagId == selectedTag?.id
    }

    fun getTagInfo() {
        ioScope.launch {
            Timber.d("getTagInfo $tagId")
            getTagEntryData(tagId)
            if (showGraph && selected) getGraphData(tagId)
        }
    }

    private fun getGraphData(tagId: String) {
        Timber.d("Get graph data for tagId = $tagId")
        ioScope.launch {
            tagDetailsInteractor
                .getTagReadings(tagId)
                ?.let {
                    withContext(Dispatchers.Main) {
                        tagReadings.value = it
                    }
                }
        }
    }

    private fun getTagEntryData(tagId: String) {
        Timber.d("getTagEntryData for tagId = $tagId")
        ioScope.launch {
            tagDetailsInteractor
                .getTagById(tagId)
                ?.let {
                    withContext(Dispatchers.Main) {
                        tagEntry.value = it
                    }
                }
        }
    }

    fun getTemperatureString(tag: Sensor): String =
        tagDetailsInteractor.getTemperatureString(tag)

    fun getTemperatureStringWithoutUnit(tag: Sensor): String =
        tagDetailsInteractor.getTemperatureStringWithoutUnit(tag)

    fun getTemperatureUnitString(): String =
        tagDetailsInteractor.getTemperatureUnitString()

    fun getHumidityString(tag: Sensor): String =
        tagDetailsInteractor.getHumidityString(tag)

    fun getPressureString(tag: Sensor): String =
        tagDetailsInteractor.getPressureString(tag)

    fun getSignalString(tag: Sensor): String =
        tagDetailsInteractor.getSignalString(tag)

    fun getLightString(tag: Sensor): String =
        tagDetailsInteractor.getLightString(tag)

    fun getSoundString(tag: Sensor): String =
        tagDetailsInteractor.getSoundString(tag)

    fun getMovementString(tag: Sensor): String =
        tagDetailsInteractor.getMovementString(tag)

    fun getMagneticString(tag:Sensor): String =
        tagDetailsInteractor.getMagneticString(tag)

    fun getCO2String(tag: Sensor): String =
        tagDetailsInteractor.getCO2String(tag)

    override fun onCleared() {
        super.onCleared()
        Timber.d("TagViewModel cleared!")
    }

}