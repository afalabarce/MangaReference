package dev.afalabarce.mangaref.data.repository.features.planets.mappers

import dev.afalabarce.mangaref.data.repository.features.characters.mappers.toDomain
import dev.afalabarce.mangaref.domain.models.features.planets.DragonBallPlanet
import dev.afalabarce.mangaref.models.features.planets.local.CachedDragonBallPlanet
import dev.afalabarce.mangaref.models.features.planets.remote.RemoteDragonBallPlanet

fun CachedDragonBallPlanet.toDomain(): DragonBallPlanet = DragonBallPlanet(
    id = id,
    name = name,
    description = description,
    image = image,
    residents = emptyList(),
)

fun RemoteDragonBallPlanet.toDomain(): DragonBallPlanet = DragonBallPlanet(
    id = id,
    name = name,
    description = description,
    image = image,
    residents =  characters.map { it.toDomain() },
)

fun RemoteDragonBallPlanet.toCached(): CachedDragonBallPlanet = CachedDragonBallPlanet(
    id = id,
    name = name,
    description = description,
    image = image,
    isDestroyed = isDestroyed,
)