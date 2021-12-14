package com.epis.proyectofinal_idnp.firebase.model

import java.util.*

class VaccinationEvent(
    var idUser: String,
    var idVaccinationLocal: Int,
    var dateEvent: Date?
    ): FirebaseEntity(documentId = null) {
        constructor() : this(idUser = "", idVaccinationLocal = 0, dateEvent = null)

    override fun toString(): String {
        return "VaccinationEvent ($idUser, $idVaccinationLocal, $dateEvent)"
    }
}
