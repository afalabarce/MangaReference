package dev.afalabarce.mangaref.data.datasources.core.features.favorites.factory

import dev.afalabarce.mangaref.data.datasources.core.db.AppDatabase
import dev.afalabarce.mangaref.data.datasources.features.favorites.FavoritesDatasource
import dev.afalabarce.mangaref.models.features.characters.local.CachedDragonBallCharacter
import kotlinx.coroutines.flow.Flow

class FavoritesDatasourceImpl(private val appDatabase: AppDatabase): FavoritesDatasource {
    override fun getAllFavorites(
        page: Int,
        limit: Int
    ): Flow<List<CachedDragonBallCharacter>> {
        TODO("Not yet implemented")
    }

    override suspend fun setFavorite(characterId: Long, isFavorite: Boolean) {
        TODO("Not yet implemented")
    }
}