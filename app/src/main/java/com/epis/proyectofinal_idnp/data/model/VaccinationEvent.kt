package com.epis.proyectofinal_idnp.data.model

import androidx.room.*
import java.util.*

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["idUser"]
        ),
        ForeignKey(
            entity = VaccinationSite::class,
            parentColumns = ["id"],
            childColumns = ["idVaccinationSite"]
        )
    ],
    indices = [Index(value = ["idUser", "idVaccinationSite"])]
)
data class VaccinationEvent(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var idUser: Int,
    @ColumnInfo(name = "idVaccinationSite", index = true)
    var idVaccinationSite: Int,
    var dateEvent: Date?
)