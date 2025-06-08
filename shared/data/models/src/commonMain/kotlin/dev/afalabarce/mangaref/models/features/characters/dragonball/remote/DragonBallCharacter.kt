package dev.afalabarce.mangaref.models.features.characters.dragonball.remote

import dev.afalabarce.mangaref.models.features.planets.dragonball.remote.DragonBallPlanet
import dev.afalabarce.mangaref.models.features.transformations.dragonball.remote.DragonBallTransformation
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DragonBallCharacter(
    @SerialName("id")
    val id: Long,
    @SerialName("name")
    val name: String,
    @SerialName("ki")
    val ki: String,
    @SerialName("maxKi")
    val maxKi: String,
    @SerialName("race")
    val race: String,
    @SerialName("gender")
    val gender: String,
    @SerialName("description")
    val description: String,
    @SerialName("image")
    val image: String,
    @SerialName("affiliation")
    val affiliation: String,
    @SerialName("transformations")
    val transformations: List<DragonBallTransformation>,
    @SerialName("originPlanet")
    val originPlanet: DragonBallPlanet,
)
