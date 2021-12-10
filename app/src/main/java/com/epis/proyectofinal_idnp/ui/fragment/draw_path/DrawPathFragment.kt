package com.epis.proyectofinal_idnp.ui.fragment.draw_path

import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Color
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.epis.proyectofinal_idnp.R
import android.util.Log
import com.epis.proyectofinal_idnp.data.model.VaccinationLocation
import com.epis.proyectofinal_idnp.databinding.FragmentDrawPathBinding
import com.epis.proyectofinal_idnp.ui.fragment.select_location.SelectLocationViewModel
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*

class DrawPathFragment : Fragment(), OnMapReadyCallback {

    companion object {
        fun newInstance() = DrawPathFragment()
    }

    private var latitudeFrom: Double = 0.0 // -16.400394
    private var longitudeFrom: Double = 0.0 // -71.5458871
    private lateinit var placeDestination: VaccinationLocation

    private lateinit var drawPathViewModel: DrawPathViewModel
    private var _binding: FragmentDrawPathBinding? = null
    private val binding get() = _binding!!

    private lateinit var mapView: MapView
    private lateinit var googleMap: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        drawPathViewModel =
            ViewModelProvider(this).get(DrawPathViewModel::class.java)
        _binding = FragmentDrawPathBinding.inflate(inflater, container, false)
        val root = binding.root
        latitudeFrom = arguments?.getDouble("latitudeFrom") ?: latitudeFrom
        longitudeFrom = arguments?.getDouble("longitudeFrom") ?: latitudeFrom
        placeDestination =
            (arguments?.getSerializable("latitudeTo") ?: VaccinationLocation("", "", "",
                1, 1, -16.400394, -71.5458871)) as VaccinationLocation
        Log.e("LatFrom", latitudeFrom.toString())
        Log.e("LatTo", placeDestination.latitude.toString())
        Log.e("LngFrom", longitudeFrom.toString())
        Log.e("LngTo", placeDestination.longitude.toString())
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapView = binding.mapDrawPath
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
        addMarkerToMap()
    }

    private fun addMarkerToMap() {
        val marker = MarkerOptions()
            .position(LatLng(placeDestination.latitude, placeDestination.longitude))
            .title("My location")
            .snippet("I'm here")
            .draggable(true)
        googleMap.addMarker(marker)
        val aqpCamera = CameraPosition.builder()
            .target(LatLng(placeDestination.latitude, placeDestination.longitude))
            .zoom(17f)
            .bearing(0f)
            .tilt(45f)
            .build()
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(aqpCamera))
    }

}