package dev.afalabarce.mangaref.data.datasources.core.features.example

import dev.afalabarce.mangaref.data.datasources.features.example.EntityDataStore
import dev.afalabarce.mangaref.models.features.example.local.CacheExampleEntity
import kotlinx.coroutines.flow.Flow

class EntityDataStoreImpl(private val dao: ExampleDao): EntityDataStore {
    override fun getEntities(): Flow<List<CacheExampleEntity>> = dao.getAllEntities()
}