package com.epis.proyectofinal_idnp.ui.fragment.profile

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.epis.proyectofinal_idnp.firebase.livedata.DocumentReferenceFirebaseLiveData
import com.epis.proyectofinal_idnp.firebase.model.User
import com.epis.proyectofinal_idnp.firebase.repository.UserRepository
import com.epis.proyectofinal_idnp.firebase.service.AuthService
import com.google.android.gms.tasks.Task

class ProfileViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Profile Fragment"
    }
    val text: LiveData<String> = _text

    private var currentUser = AuthService.firebaseGetCurrentUser()
    private var userRepository = UserRepository
    private var liveData: DocumentReferenceFirebaseLiveData<User>? = null

    fun getCurrentUserData():DocumentReferenceFirebaseLiveData<User>?{
        if (liveData == null) {
            liveData = currentUser?.let { userRepository.findById(it.uid) }
        }
        return liveData
    }

    fun getUserFbEmail(): String? {
        return currentUser?.email
    }

    fun updateCurrentUser(user: User, email: String){
        userRepository.update(user)
        currentUser?.updateEmail(email)
    }

    fun updatePassword(email: String, oldPassword: String, newPassword: String, context: Context?) {
        val auth: Task<Void> = AuthService.firebaseReauthenticationWithCredential(
            email,
            oldPassword
        )!!
        auth.addOnCompleteListener{ task ->
            if (task.isSuccessful) {
                AuthService.firebaseGetCurrentUser()?.updatePassword(newPassword)
                Toast.makeText(context, "User Password Updated", Toast.LENGTH_SHORT).show()
                Log.e("TAG", "Reauthentication Succesful")
            } else {
                Toast.makeText(context, "Password Updated Failed", Toast.LENGTH_SHORT).show()
                Log.e("TAG", "Reauthentication Failed")
            }
        }
    }

}