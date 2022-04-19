package co.edu.udea.compumovil.xtrack

import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.Typeface
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import co.edu.udea.compumovil.xtrack.util.LocationInformation
import co.edu.udea.compumovil.xtrack.viewmodel.LocationInfoViewModel
import com.google.android.gms.location.LocationRequest
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
import com.google.android.gms.tasks.CancellationTokenSource
import java.util.*
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty


class MapsFragment : Fragment() {



    var location: LatLng? = null
    private var marker: Marker? = null
    //var locationInformation = LocationInformation()

    private val locationInformation: LocationInfoViewModel by activityViewModels();

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

        checkLocation(googleMap)

    }

    private fun checkLocation(googleMap: GoogleMap) {



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

        var cancellationTokenSource = CancellationTokenSource()

        val task = LocationServices
            .getFusedLocationProviderClient(requireContext()).getCurrentLocation(
                LocationRequest.PRIORITY_HIGH_ACCURACY,
                cancellationTokenSource.token
            )
        task.addOnSuccessListener {
            if (it != null) {
                location = LatLng(it.latitude, it.longitude)
                val currentLocation = location
                val zoomLevel = 13.0f
                currentLocation?.let { CameraUpdateFactory.newLatLngZoom(it,zoomLevel) }
                    ?.let { googleMap.moveCamera(it) }
                googleMap.setOnMapClickListener { point ->

                    marker?.remove()
                    val geocoder = Geocoder(this.context, Locale.getDefault())
                    val addresses: List<Address> =
                        geocoder.getFromLocation(point.latitude, point.longitude, 1)
                    var address: Address = addresses[0];
                    locationInformation.cityName.value = address.locality
                    locationInformation.stateName.value = address.adminArea
                    locationInformation.countryName.value = address.countryName
                    locationInformation.postalCode.value = address.postalCode
                    locationInformation.thoroughfare.value = address.thoroughfare
                    locationInformation.subThoroughfare.value = address.subThoroughfare

                    if (locationInformation.cityName.value == null) {
                        locationInformation.cityName.value = ""
                    }
                    if (locationInformation.stateName.value == null) {
                        locationInformation.stateName.value = ""
                    }
                    if (locationInformation.countryName.value == null) {
                        locationInformation.countryName.value = "";
                    }
                    if (locationInformation.postalCode.value == null) {
                        locationInformation.postalCode.value = "";
                    }
                    if (locationInformation.thoroughfare.value == null) {
                        locationInformation.thoroughfare.value = "";
                    }
                    if (locationInformation.subThoroughfare.value == null) {
                        locationInformation.subThoroughfare.value = "";
                    }


                    val markerOptions =
                        MarkerOptions().position(LatLng(point.latitude, point.longitude))
                            .title(locationInformation.countryName.value)
                            .snippet(locationInformation.cityName.value + " " + locationInformation.stateName.value + " " + locationInformation.postalCode.value + "\n"
                                    + locationInformation.thoroughfare.value + " " + locationInformation.subThoroughfare.value)
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
