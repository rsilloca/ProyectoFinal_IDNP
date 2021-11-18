package com.epis.proyectofinal_idnp.firebase.repository

import com.epis.proyectofinal_idnp.firebase.model.User
import com.epis.proyectofinal_idnp.firebase.service.AuthService
import com.google.firebase.auth.FirebaseUser

object UserRepository : FirebaseRepository<User>(User::class.java) {

    private lateinit var authUser: FirebaseUser

    fun saveUser (user: com.epis.proyectofinal_idnp.data.model.User){
        authUser = AuthService.firebaseGetCurrentUser()!!

        super.save(User(
            user.fullName,
            user.phoneNumber.toInt(),
            null,
            "",
            authUser.uid
        ))
    }
}