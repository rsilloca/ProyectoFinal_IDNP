package com.epis.proyectofinal_idnp.ui.fragment.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.epis.proyectofinal_idnp.firebase.livedata.DocumentReferenceFirebaseLiveData
import com.epis.proyectofinal_idnp.firebase.model.User
import com.epis.proyectofinal_idnp.firebase.repository.UserRepository
import com.epis.proyectofinal_idnp.firebase.service.AuthService
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.Query

class ProfileViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Profile Fragment"
    }
    val text: LiveData<String> = _text

    private var currentUser = AuthService.firebaseGetCurrentUser()
    private var userRepository = UserRepository
    private var liveData: DocumentReferenceFirebaseLiveData<User>? = null

    fun getCurrentUser():DocumentReferenceFirebaseLiveData<User>?{
        if (liveData == null) {
            liveData = currentUser?.let { userRepository.findById(it.uid) }
        }
        return liveData
    }

    fun getEmailAndIdUser(): FirebaseUser? {
        return currentUser
    }

}