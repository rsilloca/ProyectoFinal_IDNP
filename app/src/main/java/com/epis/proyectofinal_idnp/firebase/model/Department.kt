package com.epis.proyectofinal_idnp.firebase.model

class Department (

    var id_departamento:Int,
    var departamento:String

    ): FirebaseEntity(documentId = null){
        constructor() : this(id_departamento=0, departamento="")

    override fun toString(): String {
        return "$id_departamento $departamento"
    }
}
