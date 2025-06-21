package dev.afalabarce.mangaref.models.features.transformations.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import dev.afalabarce.mangaref.models.features.characters.local.CachedDragonBallCharacter

@Entity(
    tableName = "character_transformations",
    foreignKeys = [
        ForeignKey(
            entity = CachedDragonBallCharacter::class,
            parentColumns = ["id"],
            childColumns = ["character_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class CachedDragonBallTransformation(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id") 
    val id: Long,
    @ColumnInfo(name = "character_id")
    val characterId: Long,
    @ColumnInfo(name = "name") 
    val name: String,
    @ColumnInfo(name = "image") 
    val image: String,
    @ColumnInfo(name = "ki") 
    val ki: String,
)
