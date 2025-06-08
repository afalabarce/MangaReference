package dev.afalabarce.mangaref.data.repository.features.preferences

import dev.afalabarce.mangaref.data.datasources.features.preferences.AppPreferences
import dev.afalabarce.mangaref.domain.repository.features.preferences.PreferencesRepository
import kotlinx.coroutines.flow.Flow

class PreferencesRepositoryImpl(private val preferences: AppPreferences) : PreferencesRepository {
    override fun getDeviceId(): Flow<Long> = this.preferences.getDeviceId()

    override suspend fun setDeviceId(deviceId: Long) = this.preferences.setDeviceId(deviceId)

}