package co.edu.udea.compumovil.xtrack.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "tblExperiencePhoto",
    foreignKeys = [ForeignKey(entity = ExperienceEntity::class,
        parentColumns = ["ExperienceId"], childColumns = ["ExperienceId"])])
data class ExperiencePhotoEntity(
    @PrimaryKey(autoGenerate = true)
    var experiencePhotoId: Long = 0L,
    @ColumnInfo(name = "ExperienceId", index = true)
    var experienceId: Long,
    @ColumnInfo(name = "CreationTime")
    var creationTime: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "PhotoUrl")
    var photoUrl: String,
    @ColumnInfo(name = "BreadCumbUrl")
    var breadCumbUrl: String
)
