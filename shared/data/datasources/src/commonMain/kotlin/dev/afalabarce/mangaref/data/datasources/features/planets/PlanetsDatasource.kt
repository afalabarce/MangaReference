package dev.afalabarce.mangaref.data.datasources.features.planets

import dev.afalabarce.mangaref.models.features.characters.remote.RemoteDragonBallCharacter
import dev.afalabarce.mangaref.models.features.pagination.PaginatedResult
import dev.afalabarce.mangaref.models.features.planets.local.CachedDragonBallPlanet
import dev.afalabarce.mangaref.models.features.planets.remote.RemoteDragonBallPlanet
import kotlinx.coroutines.flow.Flow

interface PlanetsDatasource {
    fun getAllPlanets(page: Int, limit: Int): Flow<List<CachedDragonBallPlanet>>
    fun getAllRemotePlanets(page: Int, limit: Int): Flow<PaginatedResult<RemoteDragonBallPlanet>>
    fun getPlanet(planetId: Long): Flow<CachedDragonBallPlanet>

    fun getRemotePlanet(planetId: Long): Flow<RemoteDragonBallPlanet>
    suspend fun insertAllPlanets(planets: List<CachedDragonBallPlanet>)
}