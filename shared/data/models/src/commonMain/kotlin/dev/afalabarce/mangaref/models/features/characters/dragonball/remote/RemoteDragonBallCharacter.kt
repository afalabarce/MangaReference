package dev.afalabarce.mangaref.models.features.characters.dragonball.remote

import dev.afalabarce.mangaref.models.features.planets.dragonball.remote.RemoteDragonBallPlanet
import dev.afalabarce.mangaref.models.features.transformations.dragonball.remote.RemoteDragonBallTransformation
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RemoteDragonBallCharacter(
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
    val transformations: List<RemoteDragonBallTransformation>,
    @SerialName("originPlanet")
    val originPlanet: RemoteDragonBallPlanet,
)
