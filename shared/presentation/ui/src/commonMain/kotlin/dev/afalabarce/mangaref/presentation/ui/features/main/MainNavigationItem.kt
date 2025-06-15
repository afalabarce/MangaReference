package dev.afalabarce.mangaref.presentation.ui.features.main

import dev.afalabarce.mangaref.domain.models.features.characters.DragonBallCharacter
import kotlinx.serialization.Serializable

@Serializable
object Characters
@Serializable
object Planets
@Serializable
data class CharacterDetails(val character: DragonBallCharacter)
@Serializable
data class PlanetDetails(val planet: DragonBallCharacter)