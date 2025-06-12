package dev.afalabarce.mangaref.data.datasources.core.di

import de.jensklingenberg.ktorfit.Ktorfit
import dev.afalabarce.mangaref.core.common.di.KoinModuleLoader
import dev.afalabarce.mangaref.data.datasources.core.db.getRoomDatabase
import dev.afalabarce.mangaref.data.datasources.core.features.characters.factory.CharactersDataSourceLocal
import dev.afalabarce.mangaref.data.datasources.core.features.characters.factory.CharactersDataSourceRemote
import dev.afalabarce.mangaref.data.datasources.core.features.characters.remote.DragonBallCharactersApi
import dev.afalabarce.mangaref.data.datasources.core.features.characters.remote.createDragonBallCharactersApi
import dev.afalabarce.mangaref.data.datasources.core.features.favorites.factory.FavoritesDatasourceImpl
import dev.afalabarce.mangaref.data.datasources.core.features.planets.factory.PlanetsDatasourceLocal
import dev.afalabarce.mangaref.data.datasources.core.features.planets.factory.PlanetsDatasourceRemote
import dev.afalabarce.mangaref.data.datasources.core.features.planets.remote.DragonBallPlanetsApi
import dev.afalabarce.mangaref.data.datasources.core.features.planets.remote.createDragonBallPlanetsApi
import dev.afalabarce.mangaref.data.datasources.core.features.preferences.AppPreferencesImpl
import dev.afalabarce.mangaref.data.datasources.core.remote.ApiService
import dev.afalabarce.mangaref.data.datasources.features.characters.CharactersDatasource
import dev.afalabarce.mangaref.data.datasources.features.favorites.FavoritesDatasource
import dev.afalabarce.mangaref.data.datasources.features.planets.PlanetsDatasource
import dev.afalabarce.mangaref.data.datasources.features.preferences.AppPreferences
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
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
                            .httpClient {
                                install(ContentNegotiation) {
                                    json(Json {
                                        isLenient = true
                                        ignoreUnknownKeys = true
                                        prettyPrint = true
                                    })
                                }
                            }
                            .baseUrl(ApiService.API_URL)
                            .build()
                            .createDragonBallCharactersApi()
                    }
                    single<DragonBallPlanetsApi> {
                        Ktorfit
                            .Builder()
                            .httpClient {
                                install(ContentNegotiation) {
                                    json(Json {
                                        isLenient = true
                                        ignoreUnknownKeys = true
                                        prettyPrint = true
                                    })
                                }
                            }
                            .baseUrl(ApiService.API_URL)
                            .build()
                            .createDragonBallPlanetsApi()
                    }

                    singleOf(::ApiService)
                    singleOf(::getRoomDatabase)
                    singleOf(::AppPreferencesImpl) bind AppPreferences::class
                    factory <CharactersDatasource>(named("localCharacters")){ CharactersDataSourceLocal(get()) }
                    factory <CharactersDatasource>(named("remoteCharacters")){ CharactersDataSourceRemote(get()) }
                    factory <PlanetsDatasource>(named("localPlanets")){ PlanetsDatasourceLocal(get()) }
                    factory <PlanetsDatasource>(named("remotePlanets")){ PlanetsDatasourceRemote(get()) }
                    factoryOf(::FavoritesDatasourceImpl) bind FavoritesDatasource::class
                }
            )
        ).toList()
}
