package co.edu.udea.compumovil.xtrack.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import co.edu.udea.compumovil.xtrack.util.dateToString
import java.util.*

@Entity(tableName = "tblExperience")
data class ExperienceEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="ExperienceId")
    var experienceId: Long = 0L,
    @ColumnInfo(name="Title")
    var title: String?,
    @ColumnInfo(name="ExperienceDate")
    var date: String? = dateToString(Date()),
    @ColumnInfo(name="City")
    var city: String?,
    @ColumnInfo(name="Location")
    var location: String?,
    @ColumnInfo(name="Description")
    var description: String?
)