package dev.afalabarce.mangaref.data.datasources.features.characters

import dev.afalabarce.mangaref.models.features.characters.local.CachedDragonBallCharacter
import dev.afalabarce.mangaref.models.features.characters.remote.RemoteDragonBallCharacter
import dev.afalabarce.mangaref.models.features.pagination.PaginatedResult
import kotlinx.coroutines.flow.Flow

interface CharactersDatasource {

    fun getAllCharacters(page: Int, limit: Int): Flow<List<CachedDragonBallCharacter>>
    fun getAllRemoteCharacters(page: Int, limit: Int): Flow<PaginatedResult<RemoteDragonBallCharacter>>
    fun getCharacter(characterId: Long): Flow<CachedDragonBallCharacter>
    fun getRemoteCharacter(characterId: Long): Flow<RemoteDragonBallCharacter>
    suspend fun insertAllCharacters(characters: List<CachedDragonBallCharacter>)
}