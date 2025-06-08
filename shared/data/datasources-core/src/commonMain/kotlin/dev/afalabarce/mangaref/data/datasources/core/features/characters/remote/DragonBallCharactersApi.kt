package dev.afalabarce.mangaref.data.datasources.core.features.characters.remote

import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Path
import de.jensklingenberg.ktorfit.http.Query
import dev.afalabarce.mangaref.models.features.characters.remote.RemoteDragonBallCharacter
import dev.afalabarce.mangaref.models.features.pagination.PaginatedResult

interface DragonBallCharactersApi {
    @GET("/api/characters")
    suspend fun getAllCharacters(@Query("page") page: Int, @Query("limit") limit: Int): PaginatedResult<RemoteDragonBallCharacter>

    @GET("/api/characters/{characterId}")
    suspend fun getCharacter(@Path("characterId") characterId: Int): RemoteDragonBallCharacter
}