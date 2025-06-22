package dev.afalabarce.mangaref.data.datasources.core.features.characters.factory

import dev.afalabarce.mangaref.data.datasources.core.db.AppDatabase
import dev.afalabarce.mangaref.data.datasources.features.characters.CharactersDatasource
import dev.afalabarce.mangaref.models.features.characters.local.CachedDragonBallCharacter
import dev.afalabarce.mangaref.models.features.characters.local.CachedDragonBallCharacterModel
import dev.afalabarce.mangaref.models.features.characters.remote.RemoteDragonBallCharacter
import dev.afalabarce.mangaref.models.features.pagination.PaginatedResult
import kotlinx.coroutines.flow.Flow

class CharactersDataSourceLocal(private val appDatabase: AppDatabase): CharactersDatasource {
    override fun getAllCharacters(
        page: Int,
        limit: Int
    ): Flow<List<CachedDragonBallCharacterModel>> = appDatabase.charactersDao().getAllCharacters(page, limit)
    override fun getAllRemoteCharacters(
        page: Int,
        limit: Int
    ): Flow<PaginatedResult<RemoteDragonBallCharacter>> {
        TODO("Not yet implemented")
    }

    override fun getCharacter(characterId: Long): Flow<CachedDragonBallCharacterModel> = appDatabase.charactersDao().getCharacter(characterId)

    override fun getRemoteCharacter(characterId: Long): Flow<RemoteDragonBallCharacter> {
        TODO("Not yet implemented")
    }

    override suspend fun insertAllCharacters(characters: List<CachedDragonBallCharacterModel>) {
        val planets = characters.mapNotNull { it.originPlanet }.distinctBy { it.id }
        this.appDatabase.planetsDao().insertAllPlanets(planets)
        this.appDatabase.charactersDao().insertAllCharacters(characters.map { it.character })
        val transformations = characters.flatMap { character ->
            character.transformations.map { it.copy(characterId = character.character.id) } }
        this.appDatabase.charactersDao().insertAllCharacterTransformation(transformations)
    }
}