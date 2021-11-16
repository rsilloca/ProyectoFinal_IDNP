package com.epis.proyectofinal_idnp.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.epis.proyectofinal_idnp.data.model.VaccinationEvent

@Dao
abstract class VaccinationEventDao {
    @Query("SELECT * FROM VaccinationEvent")
    abstract fun getAll(): LiveData<List<VaccinationEvent>>

    @Query("SELECT * FROM VaccinationEvent WHERE idUser = :idUser")
    abstract fun getAllVaccinationEventsFromUser(idUser: Int): LiveData<List<VaccinationEvent>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insertVaccinationEvent(vaccinationEvent: VaccinationEvent)

    @Query("DELETE FROM VaccinationEvent")
    abstract fun deleteAllVaccinationEvents()

    @Query("DELETE FROM VaccinationEvent WHERE idUser = :idUser")
    abstract fun deleteAllVaccinationEventsFromUser(idUser: Int)

    @Query("DELETE FROM VaccinationEvent WHERE id = :idVaccinationEvent")
    abstract fun deleteOneVaccinationEventFromId(idVaccinationEvent: Int)

}