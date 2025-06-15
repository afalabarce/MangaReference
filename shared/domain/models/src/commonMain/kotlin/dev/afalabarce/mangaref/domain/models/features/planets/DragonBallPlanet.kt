package dev.afalabarce.mangaref.domain.models.features.planets

import dev.afalabarce.mangaref.domain.models.features.characters.DragonBallCharacter
import kotlinx.serialization.Serializable

@Serializable
data class DragonBallPlanet(
    val id: Long,
    val name: String,
    val description: String,
    val image: String,
    val residents: List<DragonBallCharacter>
)
