package com.epis.proyectofinal_idnp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var fullName: String?,
    var phoneNumber: String?,
    var email: String?,
    var password: String?,
    var vaccinationDate: String?,
    var typeVaccine: String?
) {
    companion object {
        val AnonymousUser by lazy {
            User(
                id = 10000,
                fullName = "Anonymous User",
                phoneNumber = "0",
                email = "No E-Mail",
                password = "No Password",
                vaccinationDate = "00/00/0000",
                typeVaccine = "Without vaccine"
            )
        }
    }

    override fun toString(): String {
        return "User(id=$id, fullName=$fullName, phoneNumber=$phoneNumber, email=$email, password=$password, vaccinationDate=$vaccinationDate, typeVaccine=$typeVaccine)"
    }

}