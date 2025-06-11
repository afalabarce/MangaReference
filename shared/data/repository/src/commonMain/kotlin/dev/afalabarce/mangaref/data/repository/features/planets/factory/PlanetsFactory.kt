package dev.afalabarce.mangaref.data.repository.features.planets.factory

import dev.afalabarce.mangaref.data.datasources.features.planets.PlanetsDatasource

internal class PlanetsFactory(
    val local: PlanetsDatasource,
    val remote: PlanetsDatasource
)