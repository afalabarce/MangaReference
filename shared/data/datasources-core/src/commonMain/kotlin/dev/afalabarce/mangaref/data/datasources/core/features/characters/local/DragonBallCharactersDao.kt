package dev.afalabarce.mangaref.data.datasources.core.features.characters.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.afalabarce.mangaref.models.features.characters.local.CachedDragonBallCharacter
import dev.afalabarce.mangaref.models.features.planets.local.CachedDragonBallPlanet
import kotlinx.coroutines.flow.Flow

@Dao
interface DragonBallCharactersDao {
    @Query("select distinct * from characters order by id asc limit :limit offset :page*:limit")
    fun getAllCharacters(page: Int, limit: Int): Flow<List<CachedDragonBallCharacter>>

    @Query("select * from characters where id=:characterId")
    fun getCharacter(characterId: Long): Flow<CachedDragonBallCharacter>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacter(character: CachedDragonBallCharacter)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllCharacters(characters: List<CachedDragonBallCharacter>)

}