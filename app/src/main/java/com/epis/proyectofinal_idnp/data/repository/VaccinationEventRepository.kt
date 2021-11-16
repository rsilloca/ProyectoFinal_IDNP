package com.epis.proyectofinal_idnp.data.repository

import androidx.lifecycle.LiveData
import com.epis.proyectofinal_idnp.data.dao.VaccinationEventDao
import com.epis.proyectofinal_idnp.data.model.VaccinationEvent

class VaccinationEventRepository(private val vaccinationEventDao: VaccinationEventDao) {

    val getAllVaccinationEvents: LiveData<List<VaccinationEvent>> = vaccinationEventDao.getAll()

    fun getVaccinationEventByUserId(idUser: Int): LiveData<List<VaccinationEvent>> {
        return vaccinationEventDao.getAllVaccinationEventsFromUser(idUser)
    }

    fun insertVaccinationEvent(vaccinationEvent: VaccinationEvent) {
        vaccinationEventDao.insertVaccinationEvent(vaccinationEvent)
    }

    fun deleteVaccinationEventByUserId(idUser: Int) {
        vaccinationEventDao.deleteAllVaccinationEventsFromUser(idUser)
    }

    fun deleteVaccinationEventByEventId(idEvent: Int) {
        vaccinationEventDao.deleteOneVaccinationEventFromId(idEvent)
    }

}