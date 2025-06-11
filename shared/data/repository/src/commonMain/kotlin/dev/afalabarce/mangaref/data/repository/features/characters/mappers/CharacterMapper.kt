package dev.afalabarce.mangaref.data.repository.features.characters.mappers

import dev.afalabarce.mangaref.domain.models.features.characters.DragonBallCharacter
import dev.afalabarce.mangaref.domain.models.features.planets.DragonBallPlanet
import dev.afalabarce.mangaref.models.features.characters.local.CachedDragonBallCharacter
import dev.afalabarce.mangaref.models.features.characters.remote.RemoteDragonBallCharacter

fun CachedDragonBallCharacter.toDomain(): DragonBallCharacter = DragonBallCharacter(
    id = id,
    name = name,
    ki = ki,
    maxKi = maxKi,
    race = race,
    gender = gender,
    description = description,
    image = image,
    affiliation = affiliation,
    isFavorite = isFavorite,
    planets = emptyList<DragonBallPlanet>(),
)

fun RemoteDragonBallCharacter.toDomain(): DragonBallCharacter = DragonBallCharacter(
    id = id,
    name = name,
    ki = ki,
    maxKi = maxKi,
    race = race,
    gender = gender,
    description = description,
    image = image,
    affiliation = affiliation,
    isFavorite = false,
    planets = emptyList<DragonBallPlanet>(),
)

fun RemoteDragonBallCharacter.toCached(): CachedDragonBallCharacter = CachedDragonBallCharacter(
    id = id,
    name = name,
    ki = ki,
    maxKi = maxKi,
    race = race,
    gender = gender,
    description = description,
    image = image,
    affiliation = affiliation,
    isFavorite = false,
)