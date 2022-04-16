package co.edu.udea.compumovil.xtrack.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import co.edu.udea.compumovil.xtrack.model.ExperiencePhotoEntity

@Dao
interface ExperiencePhotoDao {
    @Insert
    fun insert(experiencePhoto: ExperiencePhotoEntity)

    @Update
    fun update(experiencePhoto: ExperiencePhotoEntity)

    @Query("SELECT * FROM tblExperiencePhoto WHERE ExperienceId=:experienceId")
    fun getExperiencePhotos(experienceId: Long): List<ExperiencePhotoEntity>
}