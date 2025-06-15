package dev.afalabarce.mangaref.domain.usecases.features.characters

import androidx.paging.Pager
import androidx.paging.PagingConfig
import dev.afalabarce.mangaref.domain.models.features.characters.DragonBallCharacter
import dev.afalabarce.mangaref.domain.repository.features.characters.CharactersRepository
import kotlinx.serialization.builtins.CharArraySerializer

class GetAllCharactersUseCase(private val charactersRepository: CharactersRepository) {
    operator fun invoke(): Pager<Int, DragonBallCharacter> {
        val config = PagingConfig(pageSize = CharactersRepository.PAGE_SIZE)
        return Pager(
            config = config,
            pagingSourceFactory = ::charactersRepository,
        )
    }
}