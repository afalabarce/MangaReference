package dev.afalabarce.mangaref.data.datasources.features.planets

import dev.afalabarce.mangaref.models.features.planets.local.CachedDragonBallPlanet
import kotlinx.coroutines.flow.Flow

interface PlanetsDatasource {
    fun getAllPlanets(page: Int, limit: Int): Flow<List<CachedDragonBallPlanet>>
    fun getPlanet(planetId: Long): Flow<CachedDragonBallPlanet>
    suspend fun insertAllPlanets(planets: List<CachedDragonBallPlanet>)
}