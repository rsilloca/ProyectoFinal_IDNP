package com.epis.proyectofinal_idnp.ui.fragment.select_location

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Configuration
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.epis.proyectofinal_idnp.databinding.FragmentSelectLocationBinding
import com.google.android.gms.maps.*
import android.content.res.Resources
import android.location.Location
import android.location.LocationListener
import android.util.Log
import com.epis.proyectofinal_idnp.R

import androidx.core.content.ContextCompat.getSystemService

import android.location.LocationManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.model.*
import com.epis.proyectofinal_idnp.ui.activity.main.MainActivity
import android.content.Intent
import android.net.Uri
import android.provider.Settings


class SelectLocationFragment : Fragment(), OnMapReadyCallback, LocationListener, GoogleMap.OnMarkerDragListener {

    companion object {
        fun newInstance() = SelectLocationFragment()
    }

    private lateinit var selectLocationViewModel: SelectLocationViewModel
    private var _binding: FragmentSelectLocationBinding? = null
    private val locationPermissionCode = 100

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var mapView: MapView
    private lateinit var googleMap: GoogleMap
    private var latitude = -16.400394
    private var longitude = -71.5458871
    private lateinit var locationManager: LocationManager
    private var locationByGps: Location? = null
    private var locationByNetwork: Location? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        selectLocationViewModel =
            ViewModelProvider(this).get(SelectLocationViewModel::class.java)

        _binding = FragmentSelectLocationBinding.inflate(inflater, container, false)
        val root: View = binding.root

        verifyLocationPermission()

        val goToVaccinationLocationsBtn = binding.nextSelectLocation
        goToVaccinationLocationsBtn.setOnClickListener {
            (activity as MainActivity).gotoVaccinationLocations(
                binding.labelLocation.text.toString(), latitude, longitude)
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapView = binding.mapSelectLocation
        if (mapView != null) {
            mapView.onCreate(null)
            mapView.onResume()
            mapView.getMapAsync(this)
        }
    }

    override fun onMapReady(_googleMap: GoogleMap) {
        context?.let { MapsInitializer.initialize(it) }
        googleMap = _googleMap
        googleMap.mapType = GoogleMap.MAP_TYPE_NORMAL
        val nightModeFlag = requireContext().resources.configuration.uiMode and
                Configuration.UI_MODE_NIGHT_MASK
        if (nightModeFlag == Configuration.UI_MODE_NIGHT_YES) {
            try {
                val success = googleMap.setMapStyle(
                    context?.let {
                        MapStyleOptions.loadRawResourceStyle(
                            it, R.raw.map_style_json
                        )
                    }
                )
                if (!success) {
                    Log.e("MapError", "Style parsing failed.")
                }
            } catch (e: Resources.NotFoundException) {
                Log.e("MapError", "Can't find style. Error: ", e)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == locationPermissionCode) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.e("Permission", "Permission Granted!")
                Toast.makeText(activity, "Permission Granted!", Toast.LENGTH_LONG).show()
                getCurrentLocation()
            }
            else {
                Toast.makeText(activity, "Permission Denied :c", Toast.LENGTH_LONG).show()
                Log.e("Permission", "Permission Denied :c")
                (activity as MainActivity).gotoHome()
            }
        }
    }

    override fun onLocationChanged(_location: Location) {
        latitude = _location.latitude
        longitude = _location.longitude
    }

    override fun onMarkerDrag(p0: Marker) {
        // To do some
    }

    override fun onMarkerDragEnd(p0: Marker) {
        latitude = p0.position.latitude
        longitude = p0.position.longitude
        Log.d("new Location", "$latitude $longitude")
    }

    override fun onMarkerDragStart(p0: Marker) {
        // To do some
    }

    private fun verifyLocationPermission() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                locationPermissionCode
            )
        } else {
            getCurrentLocation()
        }
    }

    private fun getCurrentLocation() {
        locationManager = activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val hasGps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val hasNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        val gpsLocationListener: LocationListener = object : LocationListener {
            override fun onLocationChanged(location: Location) {
                locationByGps = location
            }
            override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
            override fun onProviderEnabled(provider: String) {}
            override fun onProviderDisabled(provider: String) {}
        }
        val networkLocationListener: LocationListener = object : LocationListener {
            override fun onLocationChanged(location: Location) {
                locationByNetwork= location
            }
            override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
            override fun onProviderEnabled(provider: String) {}
            override fun onProviderDisabled(provider: String) {}
        }
        if (hasGps) {
            Log.e("GPS enabled", "GPS is enabled!")
            if (ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                (activity as MainActivity).gotoHome()
            }
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                5000,
                0F,
                gpsLocationListener
            )
        } else {
            Log.e("GPS", "GPS not enabled")
            // val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            // activity?.startActivity(intent)
        }
        if (hasNetwork) {
            Log.e("Network enabled", "Network is enabled!")
            locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                5000,
                0F,
                networkLocationListener
            )
        } else {
            Log.e("Network", "Network not enabled")
        }
        val lastKnownLocationByGps =
            locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        lastKnownLocationByGps?.let {
            locationByGps = lastKnownLocationByGps
        }
        val lastKnownLocationByNetwork =
            locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
        lastKnownLocationByNetwork?.let {
            locationByNetwork = lastKnownLocationByNetwork
        }
        if (locationByGps != null && locationByNetwork != null) {
            if (locationByGps!!.accuracy > locationByNetwork!!.accuracy) {
                latitude = locationByGps!!.latitude
                longitude = locationByGps!!.longitude
                addMarkerToMap()
            } else {
                latitude = locationByNetwork!!.latitude
                longitude = locationByNetwork!!.longitude
                addMarkerToMap()
            }
        }
    }

    private fun addMarkerToMap() {
        val marker = MarkerOptions()
            .position(LatLng(latitude, longitude))
            .title("My location")
            .snippet("I'm here")
            .draggable(true)
        googleMap.addMarker(marker)
        googleMap.setOnMarkerDragListener(this)
        val aqpCamera = CameraPosition.builder()
            .target(LatLng(latitude, longitude))
            .zoom(17f)
            .bearing(0f)
            .tilt(45f)
            .build()
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(aqpCamera))
    }

}