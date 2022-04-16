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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ExperienceViewModel(val context: Context) : ViewModel() {
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
            0,
            this._title.value,
            this._experienceDate.value,
            this._city.value,
            this._location.value,
            this._description.value
        )
        db.experienceDao.insert(experienceEntity)
    }

    companion object {
        fun getAllExperiences(context: Context): ArrayList<ExperienceViewModel> {
            val list = ArrayList<ExperienceViewModel>()

            val db = XtrackDatabase.getInstance(context)
            val experiences = db.experienceDao.getAll()

            for (experience in experiences) {
                val viewModel = ExperienceViewModel(context);
                viewModel.title.value = experience.title
                viewModel.description.value = experience.description
                viewModel.city.value = experience.city
                viewModel.location.value = experience.location
                viewModel.experienceDate.value = experience.date
                viewModel.images.add(R.mipmap.im_bello_01)

                list.add(viewModel)
            }
            return list
        }
    }
}