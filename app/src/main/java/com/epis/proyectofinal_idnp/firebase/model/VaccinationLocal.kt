package com.epis.proyectofinal_idnp.firebase.model

class VaccinationLocal (
        var id_ubigeo: Int,
        var nombre:String,
        var latitud: Double,
        var longitud: Double,
        var entidad_administra: String,
        var id_departamento: Int,
        var id_provincia: Int
    ): FirebaseEntity(documentId = null) {
    constructor() : this(id_ubigeo = 0, nombre = "", latitud = 0.0, longitud = 0.0,
        entidad_administra = "", id_departamento = 0, id_provincia = 0)

    override fun toString(): String {
        return "VaccinationLocal($id_ubigeo, $nombre, $latitud, $longitud, $entidad_administra, " +
                "$id_departamento, $id_provincia)"
    }

}