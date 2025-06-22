package dev.afalabarce.mangaref.presentation.ui.features.main

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
object Characters
@Serializable
object Planets

@Serializable
data class CharacterDetails(
    @SerialName("characterId") val characterId: Long,
)
@Serializable
data class PlanetDetails(
    @SerialName("planetId") val planetId: Long,
)