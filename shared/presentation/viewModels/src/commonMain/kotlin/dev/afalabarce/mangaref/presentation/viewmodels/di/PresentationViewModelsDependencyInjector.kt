package dev.afalabarce.mangaref.presentation.viewmodels.di

import dev.afalabarce.mangaref.core.common.di.KoinModuleLoader
import dev.afalabarce.mangaref.presentation.viewmodels.features.characters.CharactersViewModel
import dev.afalabarce.mangaref.presentation.viewmodels.features.planets.PlanetsViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

object PresentationViewModelsDependencyInjector : KoinModuleLoader {
    override val modules: List<Module>
        get() = listOf(
            module {
                viewModelOf(::CharactersViewModel)
                viewModelOf(::PlanetsViewModel)
            }
        )
}
