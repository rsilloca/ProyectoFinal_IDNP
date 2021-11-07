package com.epis.proyectofinal_idnp.ui.fragment.vaccination_locations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class VaccinationLocationsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Vaccination Locations Fragment"
    }
    val text: LiveData<String> = _text
}