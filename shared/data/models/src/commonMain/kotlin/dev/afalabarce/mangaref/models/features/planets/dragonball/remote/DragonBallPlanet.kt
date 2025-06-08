package dev.afalabarce.mangaref.models.features.planets.dragonball.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DragonBallPlanet(
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
)
