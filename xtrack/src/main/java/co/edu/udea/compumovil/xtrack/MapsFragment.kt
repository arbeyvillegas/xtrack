package co.edu.udea.compumovil.xtrack

import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.Typeface
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import co.edu.udea.compumovil.xtrack.databinding.ActivityTakeSelectPhotoBinding
import co.edu.udea.compumovil.xtrack.util.LocationInformation
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.Task
import java.util.*


class MapsFragment : Fragment() {


    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    var location: LatLng? = null
    private var marker: Marker? = null
    var locationInformation = LocationInformation()

    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        fusedLocationProviderClient =
            context?.let { LocationServices.getFusedLocationProviderClient(it) }!!
        checkLocation(googleMap)

    }

    private fun checkLocation(googleMap: GoogleMap) {

        val task: Task<Location> = fusedLocationProviderClient.lastLocation

        if (this.context?.let {
                ActivityCompat.checkSelfPermission(
                    it,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                )
            }
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this.requireActivity(),
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                101
            )
        }
        if (this.context?.let {
                ActivityCompat.checkSelfPermission(
                    it,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                )
            }
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this.requireActivity(),
                arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION),
                101
            )
        }
        if (this.context?.let {
                ActivityCompat.checkSelfPermission(
                    it,
                    android.Manifest.permission.ACCESS_BACKGROUND_LOCATION
                )
            }
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this.requireActivity(),
                arrayOf(android.Manifest.permission.ACCESS_BACKGROUND_LOCATION),
                101
            )
        }
        task.addOnSuccessListener {
            if (it != null) {
                location = LatLng(it.latitude, it.longitude)
                val currentLocation = location
                currentLocation?.let { CameraUpdateFactory.newLatLng(it) }
                    ?.let { googleMap.moveCamera(it) }
                googleMap.setOnMapClickListener { point ->

                    marker?.remove()
                    val geocoder = Geocoder(this.context, Locale.getDefault())
                    val addresses: List<Address> =
                        geocoder.getFromLocation(point.latitude, point.longitude, 1)
                    var address: Address = addresses[0];
                    locationInformation.cityName = address.locality
                    locationInformation.stateName = address.adminArea
                    locationInformation.countryName = address.countryName
                    locationInformation.postalCode = address.postalCode
                    locationInformation.thoroughfare = address.thoroughfare
                    locationInformation.subThoroughfare = address.subThoroughfare

                    if (locationInformation.cityName == null) {
                        locationInformation.cityName = ""
                    }
                    if (locationInformation.stateName == null) {
                        locationInformation.stateName = ""
                    }
                    if (locationInformation.countryName == null) {
                        locationInformation.countryName = "";
                    }
                    if (locationInformation.postalCode == null) {
                        locationInformation.postalCode = "";
                    }
                    if (locationInformation.thoroughfare == null) {
                        locationInformation.thoroughfare = "";
                    }
                    if (locationInformation.subThoroughfare == null) {
                        locationInformation.subThoroughfare = "";
                    }


                    val markerOptions =
                        MarkerOptions().position(LatLng(point.latitude, point.longitude))
                            .title(locationInformation.countryName)
                            .snippet(locationInformation.cityName + " " + locationInformation.stateName + " " + locationInformation.postalCode + "\n"
                                    + locationInformation.thoroughfare + " " + locationInformation.subThoroughfare)
                            .icon(
                                BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)
                            )
                    googleMap.setInfoWindowAdapter(object : InfoWindowAdapter {
                        override fun getInfoWindow(arg0: Marker): View? {
                            return null
                        }

                        override fun getInfoContents(marker: Marker): View? {
                            val info = LinearLayout(context)
                            info.orientation = LinearLayout.VERTICAL
                            val title = TextView(context)
                            title.setTextColor(Color.BLACK)
                            title.gravity = Gravity.CENTER
                            title.setTypeface(null, Typeface.BOLD)
                            title.text = marker.title
                            val snippet = TextView(context)
                            snippet.setTextColor(Color.GRAY)
                            snippet.text = marker.snippet
                            info.addView(title)
                            info.addView(snippet)
                            return info
                        }
                    })


                    marker = googleMap.addMarker(markerOptions)
                    marker?.showInfoWindow()

                }
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }
}