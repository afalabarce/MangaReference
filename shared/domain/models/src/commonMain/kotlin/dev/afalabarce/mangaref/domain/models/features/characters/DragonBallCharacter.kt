package dev.afalabarce.mangaref.domain.models.features.characters

import dev.afalabarce.mangaref.domain.models.features.planets.DragonBallPlanet
import kotlinx.serialization.Serializable

@Serializable
data class DragonBallCharacter(
    val id: Long,
    val name: String,
    val ki: String,
    val maxKi: String,
    val race: String,
    val gender: String,
    val description: String,
    val image: String,
    val affiliation: String,
    val isFavorite: Boolean,
    val planets: List<DragonBallPlanet>,
    val transformations: List<DragonBallTransformation>) {

    fun isEmptyCharacter() = this == empty()

    companion object {
        fun empty() = DragonBallCharacter(
            id = 0,
            name = "",
            ki = "",
            maxKi = "",
            race = "",
            gender = "",
            description = "",
            image = "",
            affiliation = "",
            isFavorite = false,
            planets = emptyList(),
            transformations = emptyList(),
        )
    }
}