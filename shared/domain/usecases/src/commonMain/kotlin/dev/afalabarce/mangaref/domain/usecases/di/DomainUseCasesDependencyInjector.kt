package dev.afalabarce.mangaref.domain.usecases.di

import dev.afalabarce.mangaref.core.common.di.KoinModuleLoader
import dev.afalabarce.mangaref.domain.usecases.features.characters.GetAllCharactersUseCase
import dev.afalabarce.mangaref.domain.usecases.features.planets.GetAllPlanetsUseCase
import dev.afalabarce.mangaref.domain.usecases.features.preferences.GetDeviceIdUseCase
import dev.afalabarce.mangaref.domain.usecases.features.preferences.SetDeviceIdUseCase
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

object DomainUseCasesDependencyInjector : KoinModuleLoader {
    override val modules: List<Module>
        get() = listOf(
            module {
                factoryOf(::GetDeviceIdUseCase)
                factoryOf(::SetDeviceIdUseCase)
                factoryOf(::GetAllCharactersUseCase)
                factoryOf(::GetAllPlanetsUseCase)
            }
        )
}
