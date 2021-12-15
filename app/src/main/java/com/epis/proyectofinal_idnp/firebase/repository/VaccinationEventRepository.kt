package com.epis.proyectofinal_idnp.firebase.repository

import com.epis.proyectofinal_idnp.firebase.livedata.MultipleDocumentReferenceLiveData
import com.epis.proyectofinal_idnp.firebase.model.VaccinationEvent
import com.google.firebase.firestore.Query

object VaccinationEventRepository: FirebaseRepository<VaccinationEvent>(VaccinationEvent::class.java) {

    fun finAllEventsBetween(date1:Long, date2:Long) : MultipleDocumentReferenceLiveData<VaccinationEvent, out Query?> {

        return  MultipleDocumentReferenceLiveData(
            collectionReference.whereGreaterThanOrEqualTo(
            "dateEvent", date1
            ).whereLessThanOrEqualTo("dateEvent", date2)
            , entityClass)
    }

}