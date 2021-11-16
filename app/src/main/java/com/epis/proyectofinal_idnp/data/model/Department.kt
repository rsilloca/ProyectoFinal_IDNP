package com.epis.proyectofinal_idnp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Department(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var nameDepartment: String?
)