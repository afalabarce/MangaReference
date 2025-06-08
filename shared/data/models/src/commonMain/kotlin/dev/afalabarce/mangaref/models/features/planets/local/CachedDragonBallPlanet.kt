package dev.afalabarce.mangaref.models.features.planets.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.afalabarce.mangaref.models.features.characters.remote.RemoteDragonBallCharacter
import kotlinx.serialization.SerialName

@Entity(tableName = "planets")
data class CachedDragonBallPlanet(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: Long,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "is_destroyed")
    val isDestroyed: Boolean,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "image")
    val image: String,
)