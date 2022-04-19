package co.edu.udea.compumovil.xtrack.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import co.edu.udea.compumovil.xtrack.model.ExperienceEntity

@Dao
interface ExperienceDao {
    @Insert
    fun insert(experience: ExperienceEntity): Long

    @Update
    fun update(experience: ExperienceEntity)

    @Query("SELECT * FROM tblExperience WHERE ExperienceId = :id")
    fun get(id: Long): ExperienceEntity?

    @Query("SELECT * FROM tblExperience ORDER BY ExperienceDate DESC")
    fun getAll(): List<ExperienceEntity>

}