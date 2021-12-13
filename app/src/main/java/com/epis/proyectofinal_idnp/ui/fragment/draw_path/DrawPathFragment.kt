package com.epis.proyectofinal_idnp.ui.fragment.draw_path

import android.content.Context
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
import android.widget.Toast

import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response

import com.android.volley.VolleyError

import org.json.JSONException

import org.json.JSONObject

import org.json.JSONArray

import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import java.lang.Exception


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

    private lateinit var request: RequestQueue
    private var routes = ArrayList<List<HashMap<String, String>>>()

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
            (arguments?.getSerializable("location") ?: VaccinationLocation("", "", "",
                1, 1, -16.400394, -71.5458871)) as VaccinationLocation
        Log.e("LatFrom", latitudeFrom.toString())
        Log.e("LatTo", placeDestination.latitude.toString())
        Log.e("LngFrom", longitudeFrom.toString())
        Log.e("LngTo", placeDestination.longitude.toString())
        val txtTitle = binding.titleDrawRoute
        txtTitle.text = "Ruta hacia ${placeDestination.title}"
        request = Volley.newRequestQueue(requireContext())
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
            .title("Local Vaccination")
            .snippet(placeDestination.title)
            .draggable(true)
        googleMap.addMarker(marker)
        val markerDest = MarkerOptions()
            .position(LatLng(latitudeFrom, longitudeFrom))
            .title("My location")
            .snippet("I'm here")
            .draggable(true)
        googleMap.addMarker(markerDest)
        val aqpCamera = CameraPosition.builder()
            .target(LatLng(placeDestination.latitude, placeDestination.longitude))
            .zoom(17f)
            .bearing(0f)
            .tilt(45f)
            .build()
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(aqpCamera))
        webServiceGetRoute(latitudeFrom, longitudeFrom, placeDestination.latitude, placeDestination.longitude)
    }

    private fun webServiceGetRoute(latFrom: Double, lngFrom: Double, latTo: Double, lngTo: Double) {
        val paramsRoute = "origin=$latFrom,$lngFrom&destination=$latTo,$lngTo"
        val paramsAditional = "sensor=true&mode=driving"
        val paramKey = "key=${resources.getString(R.string.google_maps_key)}"
        val url = "https://maps.googleapis.com/maps/api/directions/json?$paramsRoute&$paramsAditional&$paramKey"
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            {
                val jsonRoutes = it.getJSONArray("routes")
                Log.e("response", it.toString())
                for (i in 0 until jsonRoutes.length()) {
                    val jsonLegs = (jsonRoutes.get(i) as JSONObject).getJSONArray("legs")
                    val path = ArrayList<HashMap<String, String>>()
                    for (j in 0 until jsonLegs.length()) {
                        val jsonSteps = (jsonLegs.get(i) as JSONObject).getJSONArray("steps")
                        for (k in 0 until jsonSteps.length()) {
                            val polyline = ((jsonSteps.get(k) as JSONObject).get("polyline") as JSONObject).get("points")
                            val list = decodePoly(polyline.toString())
                            for (l in list.indices) {
                                val hashMap = HashMap<String, String>()
                                hashMap["lat"] = list[l].latitude.toString()
                                hashMap["lng"] = list[l].longitude.toString()
                                path.add(hashMap)
                            }
                        }
                        routes.add(path)
                    }
                }
                var center: LatLng? = null
                val points = ArrayList<LatLng>()
                val lineOptions = PolylineOptions()
                for (path in routes) {
                    for (point in path) {
                        val lat = point["lng"]?.toDouble()
                        val lng = point["lat"]?.toDouble()
                        val position = LatLng(lat!!, lng!!)
                        if (center == null) {
                            center = LatLng(lat, lng)
                        }
                        points.add(position)
                    }
                    lineOptions.addAll(points)
                    lineOptions.width(2f)
                    lineOptions.color(Color.BLUE)
                }
                googleMap.addPolyline(lineOptions)
                // googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(center!!, 15f))
            }, {
                Log.e("Error Ruta", "Error al dibujar ruta")
            })
        request.add(jsonObjectRequest)
    }

    private fun decodePoly(encoded: String): List<LatLng> {
        val poly: MutableList<LatLng> = ArrayList()
        var index = 0
        val len = encoded.length
        var lat = 0
        var lng = 0
        while (index < len) {
            var b: Int
            var shift = 0
            var result = 0
            do {
                b = encoded[index++].toInt() - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlat = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lat += dlat
            shift = 0
            result = 0
            do {
                b = encoded[index++].toInt() - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlng = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lng += dlng
            val p = LatLng(
                lat.toDouble() / 1E5,
                lng.toDouble() / 1E5
            )
            poly.add(p)
        }
        return poly
    }

}