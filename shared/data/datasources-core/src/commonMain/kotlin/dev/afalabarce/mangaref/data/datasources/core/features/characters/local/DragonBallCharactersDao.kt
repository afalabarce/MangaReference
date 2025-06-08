package dev.afalabarce.mangaref.data.datasources.core.features.characters.local

import androidx.room.Dao
import androidx.room.Query
import dev.afalabarce.mangaref.models.features.characters.local.CachedDragonBallCharacter
import kotlinx.coroutines.flow.Flow

@Dao
interface DragonBallCharactersDao {
    @Query("select * from characters limit :limit offset :page")
    fun getAllCharacters(page: Int, limit: Int): Flow<List<CachedDragonBallCharacter>>
}