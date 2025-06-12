package dev.afalabarce.mangaref.data.datasources.core.features.characters.factory

import dev.afalabarce.mangaref.data.datasources.core.remote.ApiService
import dev.afalabarce.mangaref.data.datasources.features.characters.CharactersDatasource
import dev.afalabarce.mangaref.models.features.characters.local.CachedDragonBallCharacter
import dev.afalabarce.mangaref.models.features.characters.remote.RemoteDragonBallCharacter
import dev.afalabarce.mangaref.models.features.pagination.PaginatedResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

class CharactersDataSourceRemote(private val apiService: ApiService): CharactersDatasource {
    override fun getAllRemoteCharacters(
        page: Int,
        limit: Int
    ): Flow<PaginatedResult<RemoteDragonBallCharacter>> = flow { emit(apiService.charactersApi.getAllCharacters(page, limit)) }

    override fun getRemoteCharacter(characterId: Long): Flow<RemoteDragonBallCharacter> = flow {
        emit(apiService.charactersApi.getCharacter(characterId))
    }

    override fun getAllCharacters(
        page: Int,
        limit: Int
    ): Flow<List<CachedDragonBallCharacter>> {
        TODO("Not yet implemented")
    }

    override fun getCharacter(characterId: Long): Flow<CachedDragonBallCharacter> {
        TODO("Not yet implemented")
    }



    override suspend fun insertAllCharacters(characters: List<CachedDragonBallCharacter>) {
        TODO("Not yet implemented")
    }
}