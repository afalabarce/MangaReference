package dev.afalabarce.mangaref.domain.repository.features.characters

import androidx.paging.PagingSource
import dev.afalabarce.mangaref.domain.models.features.characters.DragonBallCharacter
import kotlinx.coroutines.flow.Flow

abstract class CharactersRepository: PagingSource<Int, DragonBallCharacter>() {
    abstract fun getCharacterById(characterId: Long): Flow<DragonBallCharacter>
}