package dev.afalabarce.mangaref.domain.usecases.features.planets

import androidx.paging.Pager
import androidx.paging.PagingConfig
import dev.afalabarce.mangaref.domain.models.features.planets.DragonBallPlanet
import dev.afalabarce.mangaref.domain.repository.features.planets.PlanetsRepository

class GetAllPlanetsUseCase(private val planetsRepository: PlanetsRepository) {
    operator fun invoke(): Pager<Int, DragonBallPlanet> {
        val config = PagingConfig(pageSize = PlanetsRepository.PAGE_SIZE)
        return Pager(
            config = config,
            pagingSourceFactory = ::planetsRepository,
        )
    }
}