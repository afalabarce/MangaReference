package dev.afalabarce.mangaref.domain.repository.features.planets

import androidx.paging.PagingSource
import dev.afalabarce.mangaref.domain.models.features.planets.DragonBallPlanet
import kotlinx.coroutines.flow.Flow

abstract class PlanetsRepository: PagingSource<Int, DragonBallPlanet>() {
    abstract fun getPlanetById(planetId: Long): Flow<DragonBallPlanet>
}