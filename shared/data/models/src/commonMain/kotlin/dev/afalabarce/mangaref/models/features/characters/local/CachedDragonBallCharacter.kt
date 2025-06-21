package dev.afalabarce.mangaref.models.features.characters.local

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import dev.afalabarce.mangaref.models.features.planets.local.CachedDragonBallPlanet
import dev.afalabarce.mangaref.models.features.transformations.local.CachedDragonBallTransformation

@Entity(tableName = "characters",
    foreignKeys = [
        androidx.room.ForeignKey(
            entity = CachedDragonBallPlanet::class,
            parentColumns = ["id"],
            childColumns = ["origin_planet_id"],
            onDelete = androidx.room.ForeignKey.CASCADE
        )
    ]
)
data class CachedDragonBallCharacter(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: Long,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "ki")
    val ki: String,
    @ColumnInfo(name = "maxKi")
    val maxKi: String,
    @ColumnInfo(name = "race")
    val race: String,
    @ColumnInfo(name = "gender")
    val gender: String,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "image")
    val image: String,
    @ColumnInfo(name = "affiliation")
    val affiliation: String,
    @ColumnInfo(name = "is_favorite")
    val isFavorite: Boolean,
    @ColumnInfo(name = "origin_planet_id")
    val originPlanetId: Long?
)

data class CachedDragonBallCharacterModel(
    @Embedded
    val character: CachedDragonBallCharacter,
    @Relation(
        parentColumn = "id",
        entityColumn = "character_id"
    )
    val transformations: List<CachedDragonBallTransformation>,
    @Relation(
        parentColumn = "origin_planet_id",
        entityColumn = "id"
    )
    val originPlanet: CachedDragonBallPlanet?
)
