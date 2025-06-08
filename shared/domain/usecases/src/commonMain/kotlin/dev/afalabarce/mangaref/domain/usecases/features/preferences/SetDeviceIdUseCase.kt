package dev.afalabarce.mangaref.domain.usecases.features.preferences

import dev.afalabarce.mangaref.domain.repository.features.preferences.PreferencesRepository

class SetDeviceIdUseCase(private val repository: PreferencesRepository) {
    suspend operator fun invoke(deviceId: Long) = this.repository.setDeviceId(deviceId)
}
