package dev.afalabarce.mangaref.data.datasources.core.features.characters.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import dev.afalabarce.mangaref.models.features.characters.local.CachedDragonBallCharacter
import dev.afalabarce.mangaref.models.features.characters.local.CachedDragonBallCharacterModel
import dev.afalabarce.mangaref.models.features.planets.local.CachedDragonBallPlanet
import dev.afalabarce.mangaref.models.features.transformations.local.CachedDragonBallTransformation
import kotlinx.coroutines.flow.Flow

@Dao
interface DragonBallCharactersDao {
    @Query("select distinct * from characters order by id asc limit :limit offset :page*:limit")
    fun getAllCharacters(page: Int, limit: Int): Flow<List<CachedDragonBallCharacterModel>>

    @Query("select * from characters where id=:characterId")
    fun getCharacter(characterId: Long): Flow<CachedDragonBallCharacterModel>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCharacter(character: CachedDragonBallCharacter)

    @Upsert
    suspend fun insertAllCharacters(characters: List<CachedDragonBallCharacter>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllCharacterTransformation(characterTransformations: List<CachedDragonBallTransformation>)
}
