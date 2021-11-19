package com.epis.proyectofinal_idnp.firebase.model
import com.google.firebase.firestore.Exclude;

abstract class FirebaseEntity(
    @get:Exclude
    var documentId: String ?
)