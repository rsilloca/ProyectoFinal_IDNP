package com.epis.proyectofinal_idnp.ui.fragment.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.epis.proyectofinal_idnp.firebase.livedata.MultipleDocumentReferenceLiveData
import com.epis.proyectofinal_idnp.firebase.model.VaccinationEvent
import com.epis.proyectofinal_idnp.firebase.repository.VaccinationEventRepository
import com.google.firebase.firestore.Query

class HomeViewModel : ViewModel() {

    private var vaccinationEvent = VaccinationEventRepository
    private var allLiveDataLocal: MultipleDocumentReferenceLiveData<VaccinationEvent, out Query?>? = null

    private val _text = MutableLiveData<String>().apply {
        value = "This is Home Fragment"
    }
    val text: LiveData<String> = _text

    fun finAllEventsBetween(date1: Long, date2: Long): MultipleDocumentReferenceLiveData<VaccinationEvent, out Query?>?{
        //if(allLiveDataLocal == null){
        allLiveDataLocal = vaccinationEvent.finAllEventsBetween(date1, date2)
        //}
        return allLiveDataLocal
    }
}