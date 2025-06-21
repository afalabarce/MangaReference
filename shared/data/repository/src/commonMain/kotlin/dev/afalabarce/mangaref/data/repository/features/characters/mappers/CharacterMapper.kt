package dev.afalabarce.mangaref.data.repository.features.characters.mappers

import dev.afalabarce.mangaref.data.repository.features.planets.mappers.toCached
import dev.afalabarce.mangaref.data.repository.features.planets.mappers.toDomain
import dev.afalabarce.mangaref.domain.models.features.characters.DragonBallCharacter
import dev.afalabarce.mangaref.domain.models.features.characters.DragonBallTransformation
import dev.afalabarce.mangaref.models.features.characters.local.CachedDragonBallCharacter
import dev.afalabarce.mangaref.models.features.characters.local.CachedDragonBallCharacterModel
import dev.afalabarce.mangaref.models.features.characters.remote.RemoteDragonBallCharacter
import dev.afalabarce.mangaref.models.features.transformations.local.CachedDragonBallTransformation
import dev.afalabarce.mangaref.models.features.transformations.remote.RemoteDragonBallTransformation

fun CachedDragonBallCharacterModel.toDomain(): DragonBallCharacter = DragonBallCharacter(
    id = character.id,
    name = character.name,
    ki = character.ki,
    maxKi = character.maxKi,
    race = character.race,
    gender = character.gender,
    description = character.description,
    image = character.image,
    affiliation = character.affiliation,
    isFavorite = character.isFavorite,
    planets = if (this.originPlanet == null) emptyList() else listOf(this.originPlanet!!.toDomain()),
    transformations = this.transformations.map { it.toDomain() }
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
    planets = if (originPlanet == null) emptyList() else listOf(originPlanet!!.toDomain()),
    transformations = transformations.map { it.toDomain() }.toList()
)

fun RemoteDragonBallTransformation.toDomain(): DragonBallTransformation = DragonBallTransformation(
    id = id,
    name = name,
    image = image,
    ki = ki,
)

fun RemoteDragonBallCharacter.toCached(): CachedDragonBallCharacterModel =
    CachedDragonBallCharacterModel(
        character = CachedDragonBallCharacter(
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
            originPlanetId = originPlanet?.id
        ),
        transformations = emptyList(),
        originPlanet = originPlanet?.toCached(),
    )

fun CachedDragonBallTransformation.toDomain (): DragonBallTransformation = DragonBallTransformation(
    id = id,
    name = name,
    image = image,
    ki = ki,
)