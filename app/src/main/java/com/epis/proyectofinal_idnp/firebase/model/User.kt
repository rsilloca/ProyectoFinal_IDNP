package com.epis.proyectofinal_idnp.firebase.model

import java.util.*

class User (

    var fullname:String,
    var phoneNumber: Int,
    var vaccinatedDate: Date?,
    var vaccineType: String,

    ): FirebaseEntity(documentId = null){
        constructor() : this(fullname="", phoneNumber = 0, vaccinatedDate = null, vaccineType = "")

    override fun toString(): String {
        return "User(fullname='$fullname', phoneNumber=$phoneNumber, vaccinatedDate=$vaccinatedDate, vaccineType='$vaccineType')"
    }

}
