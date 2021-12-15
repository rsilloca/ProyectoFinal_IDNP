package com.epis.proyectofinal_idnp.ui.fragment.statistics

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.epis.proyectofinal_idnp.firebase.livedata.MultipleDocumentReferenceLiveData
import com.epis.proyectofinal_idnp.firebase.model.VaccinationEvent
import com.epis.proyectofinal_idnp.firebase.repository.VaccinationEventRepository
import com.google.firebase.firestore.Query

class StatisticsViewModel : ViewModel() {

    private var vaccinationEvent = VaccinationEventRepository
    private var allLiveDataLocal: MultipleDocumentReferenceLiveData<VaccinationEvent, out Query?>? = null

    private val _text = MutableLiveData<String>().apply {
        value = "This is Statistics Fragment"
    }
    val text: LiveData<String> = _text

    fun getAllVaccinationEvent(): MultipleDocumentReferenceLiveData<VaccinationEvent, out Query?>? {
        if(allLiveDataLocal == null) {
            allLiveDataLocal = vaccinationEvent.findAll()
        }
        return allLiveDataLocal
    }

}