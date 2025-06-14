package dev.afalabarce.mangaref.data.repository.features.planets.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import dev.afalabarce.mangaref.data.repository.features.planets.factory.PlanetsFactory
import dev.afalabarce.mangaref.data.repository.features.planets.mappers.toCached
import dev.afalabarce.mangaref.data.repository.features.planets.mappers.toDomain
import dev.afalabarce.mangaref.domain.models.features.planets.DragonBallPlanet
import dev.afalabarce.mangaref.domain.repository.features.planets.PlanetsRepository
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onEmpty

internal class PlanetsRepositoryImpl internal constructor(private val factory: PlanetsFactory): PlanetsRepository() {
    override fun getPlanetById(planetId: Long): Flow<DragonBallPlanet> = channelFlow {
        factory.local.getPlanet(planetId).onEmpty {
            val response = factory.remote.getRemotePlanet(planetId).first()
            factory.local.insertAllPlanets(listOf(response.toCached()))
            send(response.toDomain())
        } .collect { cached ->
            send(cached.toDomain())
        }
    }

    override fun getRefreshKey(state: PagingState<Int, DragonBallPlanet>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DragonBallPlanet> {
        val pageNumber = params.key ?: 1
        return try {
            val prevKey = if (pageNumber == 1) null else pageNumber - 1
            val cachedResponse = factory.local.getAllPlanets(pageNumber, params.loadSize).first()

            if (cachedResponse.size < params.loadSize) {
                val response = factory.remote.getAllRemotePlanets(page = pageNumber, limit = params.loadSize).first()
                val nextKey = if (pageNumber < response.meta.totalPages) pageNumber + 1 else null

                factory.local.insertAllPlanets(response.items.map { remote -> remote.toCached() })

                PagingSource.LoadResult.Page(data = response.items.map { remote -> remote.toDomain() }, prevKey = prevKey, nextKey = nextKey)
            } else {
                val nextKey = pageNumber + 1

                PagingSource.LoadResult.Page(
                    data = cachedResponse.map { cached -> cached.toDomain() },
                    prevKey = prevKey,
                    nextKey = nextKey
                )
            }
        } catch (e: Exception) {
            Napier.w("Error AFA: ${e.message}")
            PagingSource.LoadResult.Error(e)
        }
    }
}