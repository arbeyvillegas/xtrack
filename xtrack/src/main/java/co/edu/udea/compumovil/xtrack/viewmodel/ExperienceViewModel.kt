package co.edu.udea.compumovil.xtrack.viewmodel
import android.util.Log
import androidx.lifecycle.ViewModel

class ExperienceViewModel : ViewModel() {
    init {
        Log.i("ExperienceViewModel", "ExperienceViewModel created!")
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("ExperienceViewModel", "ExperienceViewModel destroyed!")
    }
}