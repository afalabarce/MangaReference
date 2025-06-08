package dev.afalabarce.mangaref.models.features.characters.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "characters")
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
)
