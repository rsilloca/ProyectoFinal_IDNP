package com.epis.proyectofinal_idnp.firebase.service

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

object AuthService {

    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    fun firebaseSingInWithEmail(email:String, password:String): Task<AuthResult> {
        return firebaseAuth.signInWithEmailAndPassword(email, password)
    }

    fun firebaseSingOut(){
        return firebaseAuth.signOut()
    }

    fun firebaseGetCurrentUser(): FirebaseUser? {
        return firebaseAuth.currentUser
    }

    fun firebaseRegister(email: String, password: String): Task<AuthResult> {
        return firebaseAuth.createUserWithEmailAndPassword(email, password)
    }

}