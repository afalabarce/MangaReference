package dev.afalabarce.mangaref.presentation.viewmodels.features.planets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dev.afalabarce.mangaref.domain.models.features.planets.DragonBallPlanet
import dev.afalabarce.mangaref.domain.usecases.features.planets.GetAllPlanetsUseCase
import kotlinx.coroutines.flow.Flow

class PlanetsViewModel(
    private val getAllPlanetsUseCase: GetAllPlanetsUseCase
): ViewModel() {
    val planets: Flow<PagingData<DragonBallPlanet>> by lazy { getAllPlanetsUseCase(limit = PAGE_SIZE).flow.cachedIn(viewModelScope) }

    companion object {
        const val PAGE_SIZE = 10
    }
}