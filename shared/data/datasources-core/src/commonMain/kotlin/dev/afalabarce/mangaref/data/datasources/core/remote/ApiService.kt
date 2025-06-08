package dev.afalabarce.mangaref.data.datasources.core.remote

import dev.afalabarce.mangaref.data.datasources.core.features.characters.remote.DragonBallCharactersApi
import dev.afalabarce.mangaref.data.datasources.core.features.planets.remote.DragonBallPlanetsApi

class ApiService(
    val charactersApi: DragonBallCharactersApi,
    val planetsApi: DragonBallPlanetsApi
) {
    companion object {
        const val API_URL = "https://dragonball-api.com"
    }
}
