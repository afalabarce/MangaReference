package dev.afalabarce.mangaref.presentation.ui

import dev.afalabarce.mangaref.presentation.ui.di.PresentationUiDependencyInjector
import org.koin.core.context.startKoin

fun initKoin() {
    startKoin {
        modules(
            PresentationUiDependencyInjector.modules
        )
    }
}
