package dev.afalabarce.mangaref.data.datasources.core.features.planets.factory

import dev.afalabarce.mangaref.data.datasources.core.db.AppDatabase
import dev.afalabarce.mangaref.data.datasources.features.planets.PlanetsDatasource
import dev.afalabarce.mangaref.models.features.pagination.PaginatedResult
import dev.afalabarce.mangaref.models.features.planets.local.CachedDragonBallPlanet
import dev.afalabarce.mangaref.models.features.planets.remote.RemoteDragonBallPlanet
import kotlinx.coroutines.flow.Flow

class PlanetsDatasourceLocal(private val appDatabase: AppDatabase): PlanetsDatasource {
    override fun getAllPlanets(
        page: Int,
        limit: Int
    ): Flow<List<CachedDragonBallPlanet>> = appDatabase.planetsDao().getAllPlanets(page, limit)

    override fun getPlanet(planetId: Long): Flow<CachedDragonBallPlanet> = appDatabase.planetsDao().getPlanet(planetId)

    override suspend fun insertAllPlanets(planets: List<CachedDragonBallPlanet>) {
        this.appDatabase.planetsDao().insertAllPlanets(planets)
    }

    override fun getAllRemotePlanets(
        page: Int,
        limit: Int
    ): Flow<PaginatedResult<RemoteDragonBallPlanet>> {
        TODO("Not yet implemented")
    }

    override fun getRemotePlanet(planetId: Long): Flow<RemoteDragonBallPlanet> {
        TODO("Not yet implemented")
    }
}