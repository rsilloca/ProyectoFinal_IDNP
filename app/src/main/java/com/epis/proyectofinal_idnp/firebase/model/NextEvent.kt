package com.epis.proyectofinal_idnp.firebase.model

class NextEvent (
        var fecha: String,
        var horainicio: String,
        var horafin: String
    ): FirebaseEntity(documentId = null){
    constructor() : this(fecha = "", horainicio = "", horafin = "")

    override fun toString(): String {
        return "Next Event ($fecha, $horainicio, $horafin)"
    }
}