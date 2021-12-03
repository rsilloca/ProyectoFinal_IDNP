package com.epis.proyectofinal_idnp.ui.fragment.select_location

import android.content.res.Configuration
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.epis.proyectofinal_idnp.databinding.FragmentSelectLocationBinding
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import android.content.res.Resources
import android.util.Log
import com.epis.proyectofinal_idnp.R

import com.google.android.gms.maps.model.MapStyleOptions




class SelectLocationFragment : Fragment(), OnMapReadyCallback {

    companion object {
        fun newInstance() = SelectLocationFragment()
    }

    private lateinit var selectLocationViewModel: SelectLocationViewModel
    private var _binding: FragmentSelectLocationBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var mapView: MapView
    private lateinit var googleMap: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        selectLocationViewModel =
            ViewModelProvider(this).get(SelectLocationViewModel::class.java)

        _binding = FragmentSelectLocationBinding.inflate(inflater, container, false)
        val root: View = binding.root
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
        if (_googleMap != null) {
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
            googleMap.addMarker(
                MarkerOptions()
                    .position(LatLng(-16.400394, -71.5458871))
                    .title("Arequipa")
                    .snippet("I hope to go there")
            )
            val aqpCamera = CameraPosition.builder()
                .target(LatLng(-16.400394, -71.5458871))
                .zoom(17f)
                .bearing(0f)
                .tilt(45f)
                .build()
            googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(aqpCamera))
        }
    }

}