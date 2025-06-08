package dev.afalabarce.mangaref.data.datasources.core.features.planets.factory

import dev.afalabarce.mangaref.data.datasources.core.db.AppDatabase
import dev.afalabarce.mangaref.data.datasources.features.planets.PlanetsDatasource
import dev.afalabarce.mangaref.models.features.planets.local.CachedDragonBallPlanet
import kotlinx.coroutines.flow.Flow

class PlanetsDatasourceLocal(private val appDatabase: AppDatabase): PlanetsDatasource {
    override fun getAllPlanets(
        page: Int,
        limit: Int
    ): Flow<List<CachedDragonBallPlanet>> {
        TODO("Not yet implemented")
    }

    override fun getPlanet(planetId: Long): Flow<CachedDragonBallPlanet> {
        TODO("Not yet implemented")
    }

    override suspend fun insertAllPlanets(planets: List<CachedDragonBallPlanet>) {
        TODO("Not yet implemented")
    }
}