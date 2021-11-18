package com.epis.proyectofinal_idnp.firebase.model

import java.util.*

//fullname, phonenumber, vaccineType, vaccinDate
/*
* firebase me provee el servicio de datos
* */

class User (

    var fullname:String,
    var phoneNumber: Int,
    var vaccinatedDate: Date?,
    var vaccineType: String,
    var UIDAuthUser: String

    ): FirebaseEntity(documentId = null){
        constructor() : this(fullname="", phoneNumber = 0, vaccinatedDate = null, vaccineType = "", UIDAuthUser = "")

    override fun toString(): String {
        return "User(fullname='$fullname', phoneNumber=$phoneNumber, vaccinatedDate=$vaccinatedDate, vaccineType='$vaccineType', UIDAuthUser='$UIDAuthUser')"
    }

}
