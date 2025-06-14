package dev.afalabarce.mangaref.presentation.ui

import dev.afalabarce.mangaref.presentation.ui.di.PresentationUiDependencyInjector
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import org.koin.core.context.startKoin

fun initKoin() {
    Napier.base(DebugAntilog())
    startKoin {
        modules(
            PresentationUiDependencyInjector.modules
        )
    }
}
