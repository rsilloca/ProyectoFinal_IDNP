package com.epis.proyectofinal_idnp.ui.fragment.vaccination_locations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.epis.proyectofinal_idnp.firebase.livedata.MultipleDocumentReferenceLiveData
import com.epis.proyectofinal_idnp.firebase.model.VaccinationLocal
import com.epis.proyectofinal_idnp.firebase.repository.VaccionationLocalRepository
import com.google.firebase.firestore.Query

class VaccinationLocationsViewModel : ViewModel() {

    private var vaccionationLocal = VaccionationLocalRepository
    private var allLiveDataLocal: MultipleDocumentReferenceLiveData<VaccinationLocal, out Query?>? = null

    private val _text = MutableLiveData<String>().apply {
        value = "This is Vaccination Locations Fragment"
    }
    val text: LiveData<String> = _text

    fun getAllVaccionationLocalListLiveData() : MultipleDocumentReferenceLiveData<VaccinationLocal, out Query?>? {
        if(allLiveDataLocal == null) {
            allLiveDataLocal = vaccionationLocal.findAll()
        }
        return allLiveDataLocal
    }

}