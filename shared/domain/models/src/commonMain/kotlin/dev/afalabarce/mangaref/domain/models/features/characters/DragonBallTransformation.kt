package dev.afalabarce.mangaref.domain.models.features.characters

import kotlinx.serialization.Serializable

@Serializable
data class DragonBallTransformation(
    val id: Long,
    val name: String,
    val image: String,
    val ki: String,
)
