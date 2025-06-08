package dev.afalabarce.mangaref.data.datasources.core.di

import de.jensklingenberg.ktorfit.Ktorfit
import dev.afalabarce.mangaref.core.common.di.KoinModuleLoader
import dev.afalabarce.mangaref.data.datasources.core.db.getCharactersDao
import dev.afalabarce.mangaref.data.datasources.core.db.getRoomDatabase
import dev.afalabarce.mangaref.data.datasources.core.features.characters.remote.DragonBallCharactersApi
import dev.afalabarce.mangaref.data.datasources.core.features.characters.remote.createDragonBallCharactersApi
import dev.afalabarce.mangaref.data.datasources.core.features.planets.remote.DragonBallPlanetsApi
import dev.afalabarce.mangaref.data.datasources.core.features.planets.remote.createDragonBallPlanetsApi
import dev.afalabarce.mangaref.data.datasources.core.features.preferences.AppPreferencesImpl
import dev.afalabarce.mangaref.data.datasources.core.remote.ApiService
import dev.afalabarce.mangaref.data.datasources.features.preferences.AppPreferences
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect fun getPlatformInjects(): List<Module>

object DataSourceCoreDependencyInjector : KoinModuleLoader {
    override val modules: List<Module>
        get() = getPlatformInjects().union(
            listOf(
                module {
                    single<DragonBallCharactersApi> {
                        Ktorfit
                            .Builder()
                            .baseUrl(ApiService.API_URL)
                            .build()
                            .createDragonBallCharactersApi()
                    }
                    single<DragonBallPlanetsApi> {
                        Ktorfit
                            .Builder()
                            .baseUrl(ApiService.API_URL)
                            .build()
                            .createDragonBallPlanetsApi()
                    }

                    singleOf(::ApiService)
                    singleOf(::getRoomDatabase)
                    singleOf(::getCharactersDao)
                    singleOf(::AppPreferencesImpl) bind AppPreferences::class
                }
            )
        ).toList()
}
