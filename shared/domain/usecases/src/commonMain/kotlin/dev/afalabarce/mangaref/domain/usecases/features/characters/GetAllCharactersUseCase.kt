package dev.afalabarce.mangaref.domain.usecases.features.characters

import androidx.paging.Pager
import androidx.paging.PagingConfig
import dev.afalabarce.mangaref.domain.models.features.characters.DragonBallCharacter
import dev.afalabarce.mangaref.domain.repository.features.characters.CharactersRepository

class GetAllCharactersUseCase(private val charactersRepository: CharactersRepository) {
    operator fun invoke(limit: Int): Pager<Int, DragonBallCharacter> {
        val config = PagingConfig(pageSize = limit)
        return Pager(
            config = config,
            pagingSourceFactory = ::charactersRepository,
        )
    }
}