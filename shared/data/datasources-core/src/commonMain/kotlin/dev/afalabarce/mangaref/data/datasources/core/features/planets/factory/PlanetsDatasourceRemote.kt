package dev.afalabarce.mangaref.data.datasources.core.features.planets.factory

import dev.afalabarce.mangaref.data.datasources.core.remote.ApiService
import dev.afalabarce.mangaref.data.datasources.features.planets.PlanetsDatasource
import dev.afalabarce.mangaref.models.features.characters.remote.RemoteDragonBallCharacter
import dev.afalabarce.mangaref.models.features.pagination.PaginatedResult
import dev.afalabarce.mangaref.models.features.planets.local.CachedDragonBallPlanet
import dev.afalabarce.mangaref.models.features.planets.remote.RemoteDragonBallPlanet
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PlanetsDatasourceRemote(private val apiService: ApiService): PlanetsDatasource {
    override fun getAllPlanets(
        page: Int,
        limit: Int
    ): Flow<List<CachedDragonBallPlanet>> {
        TODO("Not yet implemented")
    }

    override fun getAllRemotePlanets(
        page: Int,
        limit: Int
    ): Flow<PaginatedResult<RemoteDragonBallPlanet>> = flow { emit(apiService.planetsApi.getAllPlanets(page, limit)) }

    override fun getPlanet(planetId: Long): Flow<CachedDragonBallPlanet> {
        TODO("Not yet implemented")
    }

    override fun getRemotePlanet(planetId: Long): Flow<RemoteDragonBallPlanet> = flow {
        emit(apiService.planetsApi.getPlanet(planetId))
    }

    override suspend fun insertAllPlanets(planets: List<CachedDragonBallPlanet>) {
        TODO("Not yet implemented")
    }
}