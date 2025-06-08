package dev.afalabarce.mangaref.data.datasources.features.example

import dev.afalabarce.mangaref.models.features.example.local.CacheExampleEntity
import kotlinx.coroutines.flow.Flow

interface EntityDataStore {
    fun getEntities(): Flow<List<CacheExampleEntity>>
}