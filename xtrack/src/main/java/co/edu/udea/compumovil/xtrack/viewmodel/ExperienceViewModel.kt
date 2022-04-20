package co.edu.udea.compumovil.xtrack.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import co.edu.udea.compumovil.xtrack.R
import co.edu.udea.compumovil.xtrack.database.ExperienceDao
import co.edu.udea.compumovil.xtrack.database.XtrackDatabase
import co.edu.udea.compumovil.xtrack.model.ExperienceEntity
import co.edu.udea.compumovil.xtrack.model.ExperiencePhotoEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ExperienceViewModel(val context: Context) : ViewModel() {

    private var _experienceId: Long = 0L
    var experienceId: Long
        get() = _experienceId
        set(value) {
            _experienceId = value
        }

    private var _title = MutableLiveData<String>()
    var title: MutableLiveData<String>
        get() = _title
        set(value) {
            _title = value
        }

    private var _description = MutableLiveData<String>()
    var description: MutableLiveData<String>
        get() = _description
        set(value) {
            _description = value
        }

    private var _city = MutableLiveData<String>()
    var city: MutableLiveData<String>
        get() = _city
        set(value) {
            _city = value
        }

    private var _location = MutableLiveData<String>()
    var location: MutableLiveData<String>
        get() = _location
        set(value) {
            _location = value
        }

    private var _experienceDate = MutableLiveData<String>()
    var experienceDate: MutableLiveData<String>
        get() = _experienceDate
        set(value) {
            _experienceDate = value
        }


    private val _images = ArrayList<Int>()
    val images: ArrayList<Int>
        get() = _images

    private val _photos = ArrayList<ExperiencePhoto>()
    val photos: ArrayList<ExperiencePhoto>
        get() = _photos



    private var _updating: Boolean = false
    var updating: Boolean
        get() = _updating
        set(value) {
            _updating = value
        }

    data class ExperiencePhoto (
        val idPhoto: Long = 0L,
        var photoUri: String?
    )

    init {
        Log.i("ExperienceViewModel", "ExperienceViewModel created!")
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("ExperienceViewModel", "ExperienceViewModel destroyed!")
    }

    fun saveExperience() {
        val db = XtrackDatabase.getInstance(context)
        val experienceEntity = ExperienceEntity(
            this._experienceId,
            this._title.value,
            this._experienceDate.value,
            this._city.value,
            this._location.value,
            this._description.value
        )
        if (this._updating)
            db.experienceDao.update(experienceEntity)
        else
            this._experienceId= db.experienceDao.insert(experienceEntity)

        savePhotos()
    }

    fun savePhotos() {
        val db = XtrackDatabase.getInstance(context)
        for (photo in photos) {
            if(photo.idPhoto <= 0) {
                val photoEntity = ExperiencePhotoEntity(0L, _experienceId,
                    System.currentTimeMillis(), photo.photoUri!!, null)
                db.experiencePhotoDao.insert(photoEntity)
            }
        }
    }

    fun loadExperience(experienceId: Long) {
        val db = XtrackDatabase.getInstance(context)
        val experienceEntity = db.experienceDao.get(experienceId)
        this.experienceId = experienceEntity!!.experienceId
        this.title.value = experienceEntity!!.title
        this.description.value = experienceEntity!!.description
        this.city.value = experienceEntity!!.city
        this.location.value = experienceEntity!!.location
        this.experienceDate.value = experienceEntity!!.date

        loadExperiencePhotos(experienceId)
    }

    private fun loadExperiencePhotos(experienceId: Long) {
        val db = XtrackDatabase.getInstance(context)
        val photoEntities = db.experiencePhotoDao.getExperiencePhotos(experienceId)
        for (photoEntity in photoEntities) {
            addPhoto(photoEntity)
        }
    }

    fun getSeePhotosTitle(titlePart: String) : String {
        return titlePart + " (" + _photos.size.toString() +")"
    }

    fun addPhoto(photoUri: String)
    {
        _photos.add(ExperiencePhoto(0L, photoUri))
    }

    private fun addPhoto(photoEntity: ExperiencePhotoEntity) {
        _photos.add(ExperiencePhoto(photoEntity.experiencePhotoId, photoEntity.photoUrl))
    }

    companion object {
        fun getAllExperiences(context: Context): ArrayList<ExperienceViewModel> {
            val list = ArrayList<ExperienceViewModel>()

            val db = XtrackDatabase.getInstance(context)
            val experiences = db.experienceDao.getAll()

            for (experience in experiences) {
                val viewModel = ExperienceViewModel(context);
                viewModel.experienceId = experience.experienceId
                viewModel.title.value = experience.title
                viewModel.description.value = experience.description
                viewModel.city.value = experience.city
                viewModel.location.value = experience.location
                viewModel.experienceDate.value = experience.date
                viewModel.images.add(R.mipmap.im_bello_01)

                viewModel.loadExperiencePhotos(viewModel.experienceId)

                list.add(viewModel)
            }
            return list
        }
    }
}