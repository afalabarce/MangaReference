package dev.afalabarce.mangaref.models.features.transformations.dragonball.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DragonBallTransformation(
    @SerialName("id") val id: Long,
    @SerialName("name") val name: String,
    @SerialName("image") val image: String,
    @SerialName("ki") val ki: String,
)
