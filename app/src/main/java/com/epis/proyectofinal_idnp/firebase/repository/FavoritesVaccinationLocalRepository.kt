package com.epis.proyectofinal_idnp.firebase.repository

import com.epis.proyectofinal_idnp.firebase.livedata.MultipleDocumentReferenceLiveData
import com.epis.proyectofinal_idnp.firebase.model.FavoritesVaccionationLocal
import com.google.firebase.firestore.Query

object FavoritesVaccinationLocalRepository : FirebaseRepository<FavoritesVaccionationLocal>(FavoritesVaccionationLocal::class.java){

    fun findByIdUser(idUser: String) : MultipleDocumentReferenceLiveData<FavoritesVaccionationLocal, out Query?> {
        return MultipleDocumentReferenceLiveData(collectionReference.whereEqualTo(
            "id_user", idUser
        ), entityClass)
    }

}