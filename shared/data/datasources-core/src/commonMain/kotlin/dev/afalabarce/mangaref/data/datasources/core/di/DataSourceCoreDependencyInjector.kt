package dev.afalabarce.mangaref.data.datasources.core.di

import dev.afalabarce.mangaref.core.common.di.KoinModuleLoader
import dev.afalabarce.mangaref.data.datasources.core.features.preferences.AppPreferencesImpl
import dev.afalabarce.mangaref.data.datasources.core.remote.ApiService
import dev.afalabarce.mangaref.data.datasources.features.preferences.AppPreferences
import de.jensklingenberg.ktorfit.Ktorfit
import dev.afalabarce.mangaref.data.datasources.core.db.AppDatabase
import dev.afalabarce.mangaref.data.datasources.core.db.AppDatabaseConstructor
import dev.afalabarce.mangaref.data.datasources.core.db.getExampleDao
import dev.afalabarce.mangaref.data.datasources.core.db.getRoomDatabase
import dev.afalabarce.mangaref.data.datasources.core.features.example.EntityDataStoreImpl
import dev.afalabarce.mangaref.data.datasources.core.features.example.ExampleDao
import dev.afalabarce.mangaref.data.datasources.core.remote.createApiService
import dev.afalabarce.mangaref.data.datasources.features.example.EntityDataStore
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect fun getPlatformInjects(): List<Module>

object DataSourceCoreDependencyInjector : KoinModuleLoader {
    override val modules: List<Module>
        get() = getPlatformInjects().union(
            listOf(
                module {
                    single<ApiService> {
                        Ktorfit
                            .Builder()
                            .baseUrl(ApiService.API_URL)
                            .build()
                            .createApiService()
                    }
                    singleOf(::getRoomDatabase)
                    singleOf(::getExampleDao)
                    singleOf(::AppPreferencesImpl) bind AppPreferences::class
                    factoryOf(::EntityDataStoreImpl) bind EntityDataStore::class
                    //single<AppPreferences> { AppPreferencesImpl(get()) }
                }
            )
        ).toList()
}
