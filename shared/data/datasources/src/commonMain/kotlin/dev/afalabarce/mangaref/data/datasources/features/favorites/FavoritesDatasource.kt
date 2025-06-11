package dev.afalabarce.mangaref.data.datasources.features.favorites

import dev.afalabarce.mangaref.models.features.characters.local.CachedDragonBallCharacter
import kotlinx.coroutines.flow.Flow

interface FavoritesDatasource {
    fun getAllFavorites(page: Int, limit: Int): Flow<List<CachedDragonBallCharacter>>
    suspend fun setFavorite(characterId: Long, isFavorite: Boolean)
}