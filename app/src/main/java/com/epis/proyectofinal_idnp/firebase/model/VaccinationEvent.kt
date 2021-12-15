package com.epis.proyectofinal_idnp.firebase.model

import com.google.firebase.Timestamp
import java.util.*

class VaccinationEvent(
    var departmentId: Int,
    var provinceId: Int,
    var dateEvent: Long
    ): FirebaseEntity(documentId = null) {
        constructor() : this(departmentId = 0, provinceId = 0, dateEvent = 0)

    override fun toString(): String {
        val s = Timestamp(Date(dateEvent))
        return "VaccinationEvent ($departmentId, $provinceId, ${s.toDate()})"
    }
}
