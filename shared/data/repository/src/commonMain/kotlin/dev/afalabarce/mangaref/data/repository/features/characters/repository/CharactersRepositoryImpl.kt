package dev.afalabarce.mangaref.data.repository.features.characters.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import dev.afalabarce.mangaref.data.repository.features.characters.factory.CharactersFactory
import dev.afalabarce.mangaref.data.repository.features.characters.mappers.toCached
import dev.afalabarce.mangaref.data.repository.features.characters.mappers.toDomain
import dev.afalabarce.mangaref.data.repository.features.planets.mappers.toCached
import dev.afalabarce.mangaref.domain.models.features.characters.DragonBallCharacter
import dev.afalabarce.mangaref.domain.repository.features.characters.CharactersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onEmpty

internal class CharactersRepositoryImpl internal constructor(private val factory: CharactersFactory): CharactersRepository() {

    override fun getCharacterById(characterId: Long): Flow<DragonBallCharacter> = channelFlow {
        factory.local.getCharacter(characterId).onEmpty {
            val response = factory.remote.getRemoteCharacter(characterId).first()
            factory.local.insertAllCharacters(listOf(response.toCached()))
            send(response.toDomain())
        }.collectLatest { cached ->
            if (!cached.character.isCompleted) {
                val response = factory.remote.getRemoteCharacter(characterId).first()
                factory.local.insertAllCharacters(
                    characters = listOf(
                        response.toCached().copy(
                            character = cached.character.copy(isCompleted = true, originPlanetId = response.originPlanet?.id),
                            originPlanet = response.originPlanet?.toCached(),
                            transformations = response.transformations.map { transform ->
                                transform.toCached(
                                    response.id
                                )
                            },
                        )
                    )
                )
                send(response.toDomain())
            } else {
                send(cached.toDomain())
            }
        }
    }

    override fun getRefreshKey(state: PagingState<Int, DragonBallCharacter>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: PagingSource.LoadParams<Int>): PagingSource.LoadResult<Int, DragonBallCharacter> {
        val pageNumber = params.key ?: 1
        return try {
            val prevKey = if (pageNumber == 1) null else pageNumber - 1
            val cachedResponse = factory.local.getAllCharacters(pageNumber - 1, CharactersRepository.PAGE_SIZE).first()

            if (cachedResponse.size < CharactersRepository.PAGE_SIZE) {
                val response = factory.remote.getAllRemoteCharacters(page = pageNumber, limit = CharactersRepository.PAGE_SIZE).first()
                val nextKey = if (pageNumber < response.meta.totalPages) pageNumber + 1 else null

                factory.local.insertAllCharacters(response.items.map { remote -> remote.toCached() })

                PagingSource.LoadResult.Page(data = response.items.take(CharactersRepository.PAGE_SIZE).map { remote -> remote.toDomain() }, prevKey = prevKey, nextKey = nextKey)
            } else {
                val nextKey = pageNumber + 1

                PagingSource.LoadResult.Page(
                    data = cachedResponse.take(CharactersRepository.PAGE_SIZE).map { cached -> cached.toDomain() },
                    prevKey = prevKey,
                    nextKey = nextKey
                )
            }
        } catch (e: Exception) {
            PagingSource.LoadResult.Error(e)
        }
    }
}