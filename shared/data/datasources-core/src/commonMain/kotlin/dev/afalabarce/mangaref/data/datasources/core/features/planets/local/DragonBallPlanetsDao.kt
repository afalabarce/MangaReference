package dev.afalabarce.mangaref.data.datasources.core.features.planets.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.afalabarce.mangaref.models.features.planets.local.CachedDragonBallPlanet
import kotlinx.coroutines.flow.Flow

@Dao
interface DragonBallPlanetsDao {
    @Query("select * from planets order by id asc limit :limit offset :page*:limit")
    fun getAllPlanets(page: Int, limit: Int): Flow<List<CachedDragonBallPlanet>>

    @Query("select * from planets where id=:planetId")
    fun getPlanet(planetId: Long): Flow<CachedDragonBallPlanet>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlanet(planet: CachedDragonBallPlanet)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllPlanets(planets: List<CachedDragonBallPlanet>)
}