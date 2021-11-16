package com.epis.proyectofinal_idnp.data.model

import androidx.room.*

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Province::class,
            parentColumns = ["id"],
            childColumns = ["idProvince"]
        ),
        ForeignKey(
            entity = Province::class,
            parentColumns = ["idDepartment"],
            childColumns = ["idDepartment"]
        )
    ],
    indices = [Index(value = ["idProvince", "idDepartment"])]
)
data class VaccinationSite(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var siteName: String?,
    var locationLatitude: Double?,
    var locationLongitude: Double?,
    var managingEntity: String?,
    var qualificationEESS: String?,
    @ColumnInfo(name = "idDepartment", index = true)
    var idDepartment: Int?,
    var idProvince: Int?,
)