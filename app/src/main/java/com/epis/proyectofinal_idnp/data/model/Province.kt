package com.epis.proyectofinal_idnp.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Department::class,
            parentColumns = ["id"],
            childColumns = ["idDepartment"]
        )
    ],
    indices = [
        Index(value = ["idDepartment"], unique = true)
    ]
)
data class Province(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val idDepartment: Int?,
    val nameProvince: String?
)