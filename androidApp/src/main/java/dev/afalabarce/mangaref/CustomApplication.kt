package dev.afalabarce.mangaref

import android.app.Application
import dev.afalabarce.mangaref.presentation.ui.di.PresentationUiDependencyInjector
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class CustomApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Napier.base(DebugAntilog())
        startKoin {
            androidLogger()
            androidContext(this@CustomApplication.applicationContext)
            modules(PresentationUiDependencyInjector.modules)
        }
    }
}