package dev.afalabarce.mangaref.data.datasources.core.features.characters.factory

import dev.afalabarce.mangaref.data.datasources.core.remote.ApiService
import dev.afalabarce.mangaref.data.datasources.features.characters.CharactersDatasource
import dev.afalabarce.mangaref.models.features.characters.local.CachedDragonBallCharacter
import kotlinx.coroutines.flow.Flow

class CharactersDataSourceRemote(private val apiService: ApiService): CharactersDatasource {
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