package com.epis.proyectofinal_idnp.firebase.model

import java.util.*

class VaccinationEvent(
    var departmentId: Int,
    var provinceId: Int,
    var dateEvent: String
    ): FirebaseEntity(documentId = null) {
        constructor() : this(departmentId = 0, provinceId = 0, dateEvent = "",)

    override fun toString(): String {
        return "VaccinationEvent ($departmentId, $provinceId, $dateEvent)"
    }
}
