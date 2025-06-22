@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING", "NO_ACTUAL_FOR_EXPECT")
package dev.afalabarce.mangaref.data.datasources.core.db

import androidx.room.AutoMigration
import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabase.*
import androidx.room.RoomDatabaseConstructor
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import dev.afalabarce.mangaref.data.datasources.core.features.characters.local.DragonBallCharactersDao
import dev.afalabarce.mangaref.data.datasources.core.features.favorites.local.FavoritesDao
import dev.afalabarce.mangaref.data.datasources.core.features.planets.local.DragonBallPlanetsDao
import dev.afalabarce.mangaref.models.features.characters.local.CachedDragonBallCharacter
import dev.afalabarce.mangaref.models.features.planets.local.CachedDragonBallPlanet
import dev.afalabarce.mangaref.models.features.transformations.local.CachedDragonBallTransformation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO


expect object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase> {
    override fun initialize(): AppDatabase
}

@Database(
    entities = [
        CachedDragonBallCharacter::class,
        CachedDragonBallPlanet::class,
        CachedDragonBallTransformation::class,
    ],
    version = 3,
    exportSchema = true,
    autoMigrations = [
        AutoMigration(from = 1, to = 2),
        AutoMigration(from = 2, to = 3),
    ]
)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun charactersDao(): DragonBallCharactersDao
    abstract fun favoritesDao(): FavoritesDao
    abstract fun planetsDao(): DragonBallPlanetsDao

    companion object {
        val DATABASE_NAME = "AppDatabase.db"
    }
}

fun getRoomDatabase(
    builder: Builder<AppDatabase>
): AppDatabase {
    return builder
        .addMigrations()
        .fallbackToDestructiveMigrationOnDowngrade(true)
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}
