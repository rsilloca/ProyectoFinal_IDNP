package com.epis.proyectofinal_idnp.firebase.repository

import com.epis.proyectofinal_idnp.firebase.livedata.MultipleDocumentReferenceLiveData
import com.epis.proyectofinal_idnp.firebase.model.Department
import com.epis.proyectofinal_idnp.firebase.model.Province
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.Query

object ProvinceRepository : FirebaseRepository<Province>(Province::class.java) {

    fun findByIdDepartment(idDepartment:Int) : MultipleDocumentReferenceLiveData<Province, out Query?> {

        //this.collectionReference.whereEqualTo("id_provincia", idDepartment)
        return  MultipleDocumentReferenceLiveData(collectionReference.whereEqualTo(
            "id_departamento", idDepartment
        ), entityClass)
    }

}