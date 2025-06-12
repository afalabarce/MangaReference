package dev.afalabarce.mangaref.data.datasources.core.features.planets.remote

import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Path
import de.jensklingenberg.ktorfit.http.Query
import dev.afalabarce.mangaref.models.features.pagination.PaginatedResult
import dev.afalabarce.mangaref.models.features.planets.remote.RemoteDragonBallPlanet

interface DragonBallPlanetsApi {
    @GET("api/planets")
    suspend fun getAllPlanets(@Query("page") page: Int, @Query("limit") limit: Int): PaginatedResult<RemoteDragonBallPlanet>

    @GET("api/planets/{planetId}")
    suspend fun getPlanet(@Path("planetId") planetId: Int): RemoteDragonBallPlanet
}