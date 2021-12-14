package com.epis.proyectofinal_idnp.firebase.repository

import com.epis.proyectofinal_idnp.firebase.model.VaccinationEvent

object VaccinationEventRepository: FirebaseRepository<VaccinationEvent>(VaccinationEvent::class.java) {
}