package com.epis.proyectofinal_idnp.firebase.model

class FavoritesVaccionationLocal (
    var id_local: String,
    var id_user: String
    ): FirebaseEntity(documentId = null) {
        constructor(): this (id_local = "", id_user=  "")

    override fun toString(): String {
        return "FavoritesVaccination($id_local, $id_user)"
    }

}