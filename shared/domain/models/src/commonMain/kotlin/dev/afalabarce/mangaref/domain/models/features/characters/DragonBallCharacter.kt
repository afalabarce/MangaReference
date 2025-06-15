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
    val planets: List<DragonBallPlanet>
)
