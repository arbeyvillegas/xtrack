package co.edu.udea.compumovil.xtrack.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ExperienceViewModelFactory(private val context: Context): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ExperienceViewModel::class.java)) {
            return ExperienceViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}