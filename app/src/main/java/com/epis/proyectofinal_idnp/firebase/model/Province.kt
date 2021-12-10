package com.epis.proyectofinal_idnp.firebase.model

class Province (

    var id_provincia:Int,
    var provincia:String

    ): FirebaseEntity(documentId = null){
        constructor() : this(id_provincia = 0, provincia="")
    override fun toString(): String {
        return "Provincia(id_provincia='$id_provincia', provincia='$provincia')"
    }
}