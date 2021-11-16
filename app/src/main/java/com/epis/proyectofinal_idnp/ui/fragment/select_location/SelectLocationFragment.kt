package com.epis.proyectofinal_idnp.ui.fragment.select_location

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
        val aqp = LatLng(-16.400394, -71.5458871)
        googleMap.addMarker(MarkerOptions().position(aqp).title("Marker in Arequipa"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(aqp))
    }

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