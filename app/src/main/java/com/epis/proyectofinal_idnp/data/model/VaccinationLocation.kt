package com.epis.proyectofinal_idnp.data.model

data class VaccinationLocation (
    val date: String,
    val title: String,
    val subtitle: String,
    val idDepartment: Int = 1,
    val idProvince: Int = 1,
    val latitude: Double = 0.0,
    val longitude: Double = 0.0
)