package co.edu.udea.compumovil.xtrack

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentManager
import co.edu.udea.compumovil.xtrack.databinding.ActivityMapBinding
import co.edu.udea.compumovil.xtrack.databinding.ActivityTakeSelectPhotoBinding
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment

class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var map:GoogleMap
    private lateinit var binding: ActivityMapBinding

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        binding = ActivityMapBinding.inflate(layoutInflater)
        binding.buttonSaveLocation.setOnClickListener{saveButtonClicked()}
     
    }

    private fun createFragment() {
        val mapFragment:SupportMapFragment=supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }
    private fun saveButtonClicked(){

    }

    override fun onMapReady(googleMap: GoogleMap) {
       map=googleMap
    }
}