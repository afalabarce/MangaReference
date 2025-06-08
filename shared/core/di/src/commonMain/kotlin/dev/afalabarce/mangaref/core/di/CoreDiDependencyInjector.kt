package dev.afalabarce.mangaref.core.di

import dev.afalabarce.mangaref.core.common.di.KoinModuleLoader
import dev.afalabarce.mangaref.core.ui.di.CoreUiDependencyInjector
import dev.afalabarce.mangaref.data.datasources.core.di.DataSourceCoreDependencyInjector
import dev.afalabarce.mangaref.data.repository.di.DataRepositoryDependencyInjector
import dev.afalabarce.mangaref.domain.usecases.di.DomainUseCasesDependencyInjector
import dev.afalabarce.mangaref.presentation.viewmodels.di.PresentationViewModelsDependencyInjector
import org.koin.core.module.Module

object CoreDiDependencyInjector : KoinModuleLoader {
    override val modules: List<Module>
        get() = listOf(
            DataSourceCoreDependencyInjector.modules,
            DataRepositoryDependencyInjector.modules,
            DomainUseCasesDependencyInjector.modules,
            CoreUiDependencyInjector.modules,
            PresentationViewModelsDependencyInjector.modules,
        ).flatten()
}
