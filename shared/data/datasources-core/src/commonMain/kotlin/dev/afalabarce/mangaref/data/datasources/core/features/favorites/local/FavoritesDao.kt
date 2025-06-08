package dev.afalabarce.mangaref.data.datasources.core.features.favorites.local

import androidx.room.Dao
import androidx.room.Query
import dev.afalabarce.mangaref.models.features.characters.local.CachedDragonBallCharacter
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritesDao {
    @Query("select * from characters where is_favorite=1 order by id asc limit :limit offset :page")
    fun getAllFavorites(page: Int, limit: Int): Flow<List<CachedDragonBallCharacter>>
    @Query("update characters set is_favorite=:isFavorite where id=:characterId")
    suspend fun setFavorite(characterId: Long, isFavorite: Boolean)
}