package com.lightricity.station.tag.domain

import com.lightricity.station.alarm.domain.AlarmCheckInteractor
import com.lightricity.station.app.preferences.PreferencesRepository
import com.lightricity.station.database.TagRepository
import com.lightricity.station.database.tables.RuuviTagEntity
import com.lightricity.station.util.BackgroundScanModes
import timber.log.Timber

class TagInteractor constructor(
    private val tagRepository: TagRepository,
    private val preferencesRepository: PreferencesRepository,
    private val converter: TagConverter,
    private val alarmCheckInteractor: AlarmCheckInteractor
) {

    fun getTags(isFavorite: Boolean = true): List<Sensor> =
        tagRepository
            .getAllTags(isFavorite)
            .map {
                val ruuviTag = converter.fromDatabase(it)
                val status = alarmCheckInteractor.getStatus(ruuviTag)
                ruuviTag.copy(status = status)
            }
            .also { Timber.d("TagInteractor - getTags") }

    fun getTagEntities(isFavorite: Boolean = true): List<RuuviTagEntity> =
        tagRepository.getAllTags(isFavorite)
            .also {Timber.d("TagInteractor - getTagEntities")}

    fun getTagEntityById(tagId: String): RuuviTagEntity? =
        tagRepository.getTagById(tagId)

    fun getTagByID(tagId: String): Sensor? =
        tagRepository
            .getTagById(tagId)
            ?.let { converter.fromDatabase(it) }

    fun getBackgroundScanMode(): BackgroundScanModes =
        preferencesRepository.getBackgroundScanMode()

    fun setBackgroundScanMode(mode: BackgroundScanModes) =
        preferencesRepository.setBackgroundScanMode(mode)

    fun isFirstGraphVisit(): Boolean =
        preferencesRepository.isFirstGraphVisit()

    fun setIsFirstGraphVisit(isFirst: Boolean) =
        preferencesRepository.setIsFirstGraphVisit(isFirst)

    fun isDashboardEnabled(): Boolean =
        preferencesRepository.isDashboardEnabled()
}