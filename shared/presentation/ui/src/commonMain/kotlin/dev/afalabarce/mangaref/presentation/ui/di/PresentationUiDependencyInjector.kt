package dev.afalabarce.mangaref.presentation.ui.di

import dev.afalabarce.mangaref.core.common.di.KoinModuleLoader
import dev.afalabarce.mangaref.core.di.CoreDiDependencyInjector
import org.koin.core.module.Module

object PresentationUiDependencyInjector : KoinModuleLoader {
    override val modules: List<Module>
        get() = listOf(
            CoreDiDependencyInjector.modules,
            listOf(

            )
        ).flatten()

}