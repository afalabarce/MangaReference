package dev.afalabarce.mangaref.data.repository.di

import dev.afalabarce.mangaref.core.common.di.KoinModuleLoader
import dev.afalabarce.mangaref.data.repository.features.preferences.PreferencesRepositoryImpl
import dev.afalabarce.mangaref.domain.repository.features.preferences.PreferencesRepository
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

object DataRepositoryDependencyInjector : KoinModuleLoader {
    override val modules: List<Module>
        get() = listOf(
            module {
                factoryOf(::PreferencesRepositoryImpl) bind PreferencesRepository::class
            }
        )
}
