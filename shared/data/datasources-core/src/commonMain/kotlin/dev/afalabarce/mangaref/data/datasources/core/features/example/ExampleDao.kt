package dev.afalabarce.mangaref.data.datasources.core.features.example

import androidx.room.Dao
import androidx.room.Query
import dev.afalabarce.mangaref.models.features.example.local.CacheExampleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExampleDao {
    @Query("select * from example_entities")
    fun getAllEntities(): Flow<List<CacheExampleEntity>>
}