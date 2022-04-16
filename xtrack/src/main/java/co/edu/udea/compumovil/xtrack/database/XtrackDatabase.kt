package co.edu.udea.compumovil.xtrack.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import co.edu.udea.compumovil.xtrack.model.ExperienceEntity
import co.edu.udea.compumovil.xtrack.model.ExperiencePhotoEntity

@Database(entities = [ExperienceEntity::class, ExperiencePhotoEntity::class], version = 1, exportSchema = false)
abstract class XtrackDatabase: RoomDatabase() {
    abstract val experienceDao: ExperienceDao
    abstract val experiencePhotoDao: ExperiencePhotoDao
    companion object {
        @Volatile
        private var INSTANCE: XtrackDatabase? = null
        fun getInstance(context: Context): XtrackDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        XtrackDatabase::class.java,
                        "xtrack_database")
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build()
                }
                return instance
            }
        }
    }
}