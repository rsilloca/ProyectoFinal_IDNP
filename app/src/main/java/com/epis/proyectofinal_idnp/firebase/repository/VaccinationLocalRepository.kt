package com.epis.proyectofinal_idnp.firebase.repository

import com.epis.proyectofinal_idnp.firebase.livedata.MultipleDocumentReferenceLiveData
import com.epis.proyectofinal_idnp.firebase.model.VaccinationLocal
import com.google.firebase.firestore.Query

object VaccinationLocalRepository: FirebaseRepository<VaccinationLocal>(VaccinationLocal::class.java) {

    fun findbyIdProvince(idProvince: Int) : MultipleDocumentReferenceLiveData<VaccinationLocal, out Query?>{
        return MultipleDocumentReferenceLiveData(collectionReference.whereEqualTo(
            "id_provincia", idProvince
        ), entityClass)
    }
}