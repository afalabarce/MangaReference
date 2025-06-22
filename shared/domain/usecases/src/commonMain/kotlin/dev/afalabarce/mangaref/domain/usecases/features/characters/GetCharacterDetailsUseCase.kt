package dev.afalabarce.mangaref.domain.usecases.features.characters

import dev.afalabarce.mangaref.domain.models.features.characters.DragonBallCharacter
import dev.afalabarce.mangaref.domain.repository.features.characters.CharactersRepository
import kotlinx.coroutines.flow.Flow

class GetCharacterDetailsUseCase(private val charactersRepository: CharactersRepository) {
    operator fun invoke(characterId: Long): Flow<DragonBallCharacter> = charactersRepository.getCharacterById(characterId)
}