package co.edu.udea.compumovil.xtrack

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentManager
import co.edu.udea.compumovil.xtrack.databinding.ActivityMapBinding
import co.edu.udea.compumovil.xtrack.databinding.ActivityTakeSelectPhotoBinding
import co.edu.udea.compumovil.xtrack.viewmodel.LocationInfoViewModel
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var map:GoogleMap
    private lateinit var binding: ActivityMapBinding
    private val locationInformation: LocationInfoViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        binding = ActivityMapBinding.inflate(layoutInflater)
        //binding.buttonSaveLocation.setOnClickListener{saveButtonClicked()}
        val buttonSaveLocation = findViewById<FloatingActionButton>(R.id.buttonSaveLocation)
        buttonSaveLocation.setOnClickListener{saveButtonClicked()}

     
    }

    private fun createFragment() {
        val mapFragment:SupportMapFragment=supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }
    private fun saveButtonClicked(){
        val intent = Intent()
        intent.putExtra("City", locationInformation.cityName.value)
        setResult(RESULT_OK, intent)
        finish()
    }

    override fun onMapReady(googleMap: GoogleMap) {
       map=googleMap
    }
}