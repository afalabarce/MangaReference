package dev.afalabarce.mangaref.data.datasources.features.characters

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.afalabarce.mangaref.models.features.characters.local.CachedDragonBallCharacter
import kotlinx.coroutines.flow.Flow

interface CharactersDatasource {

    fun getAllCharacters(page: Int, limit: Int): Flow<List<CachedDragonBallCharacter>>
    fun getCharacter(characterId: Long): Flow<CachedDragonBallCharacter>
    suspend fun insertAllCharacters(characters: List<CachedDragonBallCharacter>)
}