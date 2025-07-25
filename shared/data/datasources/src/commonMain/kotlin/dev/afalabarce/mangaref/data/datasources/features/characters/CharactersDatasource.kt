package dev.afalabarce.mangaref.data.datasources.features.characters

import dev.afalabarce.mangaref.models.features.characters.local.CachedDragonBallCharacter
import dev.afalabarce.mangaref.models.features.characters.local.CachedDragonBallCharacterModel
import dev.afalabarce.mangaref.models.features.characters.remote.RemoteDragonBallCharacter
import dev.afalabarce.mangaref.models.features.pagination.PaginatedResult
import kotlinx.coroutines.flow.Flow

interface CharactersDatasource {

    fun getAllCharacters(page: Int, limit: Int): Flow<List<CachedDragonBallCharacterModel>>
    fun getAllRemoteCharacters(page: Int, limit: Int): Flow<PaginatedResult<RemoteDragonBallCharacter>>
    fun getCharacter(characterId: Long): Flow<CachedDragonBallCharacterModel>
    fun getRemoteCharacter(characterId: Long): Flow<RemoteDragonBallCharacter>
    suspend fun insertAllCharacters(characters: List<CachedDragonBallCharacterModel>)

    suspend fun insertCharacter(character: CachedDragonBallCharacterModel)
}