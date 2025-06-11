package dev.afalabarce.mangaref.data.repository.features.characters.factory

import dev.afalabarce.mangaref.data.datasources.features.characters.CharactersDatasource

internal class CharactersFactory(
    val local: CharactersDatasource,
    val remote: CharactersDatasource
)