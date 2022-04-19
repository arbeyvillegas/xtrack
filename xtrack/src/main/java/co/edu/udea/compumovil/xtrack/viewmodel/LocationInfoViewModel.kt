package co.edu.udea.compumovil.xtrack.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

 class LocationInfoViewModel: ViewModel() {
     var cityName = MutableLiveData<String?>()

     var stateName = MutableLiveData<String?>()

     var countryName= MutableLiveData<String?>()

     var postalCode= MutableLiveData<String?>()

     var thoroughfare = MutableLiveData<String?>()

     var subThoroughfare = MutableLiveData<String?>()

 }
