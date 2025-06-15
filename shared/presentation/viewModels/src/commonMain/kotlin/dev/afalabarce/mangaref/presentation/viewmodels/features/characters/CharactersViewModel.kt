package dev.afalabarce.mangaref.presentation.viewmodels.features.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dev.afalabarce.mangaref.domain.models.features.characters.DragonBallCharacter
import dev.afalabarce.mangaref.domain.usecases.features.characters.GetAllCharactersUseCase
import kotlinx.coroutines.flow.Flow
import kotlin.getValue

class CharactersViewModel(
    private val getAllCharactersUseCase: GetAllCharactersUseCase
): ViewModel() {
    val characters: Flow<PagingData<DragonBallCharacter>> by lazy { getAllCharactersUseCase().flow.cachedIn(viewModelScope) }
}