package dev.afalabarce.mangaref.data.datasources.core.features.planets.local

import androidx.room.Dao
import androidx.room.Query
import dev.afalabarce.mangaref.models.features.planets.local.CachedDragonBallPlanet
import kotlinx.coroutines.flow.Flow

@Dao
interface DragonBallPlanetsDao {
    @Query("select * from planets order by id asc limit :limit offset :page")
    fun getAllPlanets(page: Int, limit: Int): Flow<List<CachedDragonBallPlanet>>

    @Query("select * from planets where id=:planetId")
    fun getPlanet(planetId: Long): Flow<CachedDragonBallPlanet>
}