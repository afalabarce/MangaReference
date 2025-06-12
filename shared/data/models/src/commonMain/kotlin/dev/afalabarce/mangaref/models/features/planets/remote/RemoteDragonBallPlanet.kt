package dev.afalabarce.mangaref.models.features.planets.remote

import dev.afalabarce.mangaref.models.features.characters.remote.RemoteDragonBallCharacter
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RemoteDragonBallPlanet(
    @SerialName("id")
    val id: Long,
    @SerialName("name")
    val name: String,
    @SerialName("isDestroyed")
    val isDestroyed: Boolean,
    @SerialName("description")
    val description: String,
    @SerialName("image")
    val image: String,
    //@SerialName("characters")
    //val characters: List<RemoteDragonBallCharacter>
)