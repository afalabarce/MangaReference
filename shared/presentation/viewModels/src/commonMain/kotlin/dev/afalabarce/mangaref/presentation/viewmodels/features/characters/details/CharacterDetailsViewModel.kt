package dev.afalabarce.mangaref.presentation.viewmodels.features.characters.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.afalabarce.mangaref.domain.models.features.characters.DragonBallCharacter
import dev.afalabarce.mangaref.domain.usecases.features.characters.GetCharacterDetailsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.getValue

class CharacterDetailsViewModel(
    private val getCharacterDetailsUseCase: GetCharacterDetailsUseCase,
): ViewModel() {
    private val _character: MutableStateFlow<DragonBallCharacter> by lazy { MutableStateFlow(DragonBallCharacter.empty()) }
val character: StateFlow<DragonBallCharacter>
    get() = _character.asStateFlow()

    fun loadCharacterById(characterId: Long) {
        viewModelScope.launch {
            getCharacterDetailsUseCase(characterId).collect { newCharacter ->
                _character.update {
                    newCharacter
                }
            }
        }
    }

    fun resetCharacter() {
        _character.update { DragonBallCharacter.empty() }
    }
}