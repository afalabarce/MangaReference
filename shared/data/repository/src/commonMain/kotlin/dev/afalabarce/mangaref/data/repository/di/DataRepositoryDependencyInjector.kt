package dev.afalabarce.mangaref.data.repository.di

import dev.afalabarce.mangaref.core.common.di.KoinModuleLoader
import dev.afalabarce.mangaref.data.repository.features.characters.factory.CharactersFactory
import dev.afalabarce.mangaref.data.repository.features.characters.repository.CharactersRepositoryImpl
import dev.afalabarce.mangaref.data.repository.features.planets.factory.PlanetsFactory
import dev.afalabarce.mangaref.data.repository.features.preferences.PreferencesRepositoryImpl
import dev.afalabarce.mangaref.domain.repository.features.characters.CharactersRepository
import dev.afalabarce.mangaref.domain.repository.features.preferences.PreferencesRepository
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

object DataRepositoryDependencyInjector : KoinModuleLoader {
    override val modules: List<Module>
        get() = listOf(
            module {
                factoryOf(::PreferencesRepositoryImpl) bind PreferencesRepository::class
                factory {
                    CharactersFactory(
                        local = get(named("localCharacters")),
                        remote = get(named("remoteCharacters"))
                    )
                }
                factory {
                    PlanetsFactory(
                        local = get(named("localPlanets")),
                        remote = get(named("remotePlanets"))
                    )
                }
                factoryOf(::CharactersRepositoryImpl) bind CharactersRepository::class
                //factoryOf(::PlanetsRepositoryImpl) bind PlanetsRepository::class
            }
        )
}
