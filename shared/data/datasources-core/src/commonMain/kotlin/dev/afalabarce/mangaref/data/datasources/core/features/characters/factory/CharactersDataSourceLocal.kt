package dev.afalabarce.mangaref.data.datasources.core.features.characters.factory

import dev.afalabarce.mangaref.data.datasources.core.db.AppDatabase
import dev.afalabarce.mangaref.data.datasources.features.characters.CharactersDatasource
import dev.afalabarce.mangaref.models.features.characters.local.CachedDragonBallCharacter
import dev.afalabarce.mangaref.models.features.characters.remote.RemoteDragonBallCharacter
import dev.afalabarce.mangaref.models.features.pagination.PaginatedResult
import kotlinx.coroutines.flow.Flow

class CharactersDataSourceLocal(private val appDatabase: AppDatabase): CharactersDatasource {
    override fun getAllCharacters(
        page: Int,
        limit: Int
    ): Flow<List<CachedDragonBallCharacter>> = appDatabase.charactersDao().getAllCharacters(page, limit)
    override fun getAllRemoteCharacters(
        page: Int,
        limit: Int
    ): Flow<PaginatedResult<RemoteDragonBallCharacter>> {
        TODO("Not yet implemented")
    }

    override fun getCharacter(characterId: Long): Flow<CachedDragonBallCharacter> {
        TODO("Not yet implemented")
    }

    override fun getRemoteCharacter(characterId: Long): Flow<RemoteDragonBallCharacter> {
        TODO("Not yet implemented")
    }

    override suspend fun insertAllCharacters(characters: List<CachedDragonBallCharacter>) {
        this.appDatabase.charactersDao().insertAllCharacters(characters)
    }
}