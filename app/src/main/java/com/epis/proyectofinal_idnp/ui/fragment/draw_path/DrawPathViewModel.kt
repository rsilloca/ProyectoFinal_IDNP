package com.epis.proyectofinal_idnp.ui.fragment.draw_path

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DrawPathViewModel : ViewModel() {

    val latitude = MutableLiveData<Double>()
    val longitude = MutableLiveData<Double>()

    fun setLocation(_latitude: Double, _longitude: Double){
        latitude.value = _latitude
        longitude.value = _longitude
    }

}