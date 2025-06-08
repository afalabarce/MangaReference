package dev.afalabarce.mangaref.presentation.viewmodels.di

import dev.afalabarce.mangaref.presentation.viewmodels.features.example.EntityExampleViewModel
import dev.afalabarce.mangaref.core.common.di.KoinModuleLoader
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

object PresentationViewModelsDependencyInjector : KoinModuleLoader {
    override val modules: List<Module>
        get() = listOf(
            module {
                viewModelOf(::EntityExampleViewModel)
            }
        )
}
