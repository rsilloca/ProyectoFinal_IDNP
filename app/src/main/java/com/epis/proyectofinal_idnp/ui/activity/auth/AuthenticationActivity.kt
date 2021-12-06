package com.epis.proyectofinal_idnp.ui.activity.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.epis.proyectofinal_idnp.R
import com.epis.proyectofinal_idnp.data.application.VaccinationApplication
import com.epis.proyectofinal_idnp.data.model.User
import com.epis.proyectofinal_idnp.databinding.ActivityAuthenticationBinding
import com.epis.proyectofinal_idnp.firebase.repository.UserRepository
import com.epis.proyectofinal_idnp.firebase.service.AuthService
import com.epis.proyectofinal_idnp.ui.activity.main.MainActivity
import com.epis.proyectofinal_idnp.ui.fragment.login.LoginFragment
import com.epis.proyectofinal_idnp.ui.fragment.register.RegisterFragment
import com.epis.proyectofinal_idnp.utils.SharedPreferencesHandler
import com.epis.proyectofinal_idnp.viewmodel.UserViewModel
import com.epis.proyectofinal_idnp.viewmodel.UserViewModelFactory
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult

class AuthenticationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthenticationBinding
    private lateinit var preferences: SharedPreferencesHandler
    private lateinit var auth: Task<AuthResult>
    private val userViewModel: UserViewModel by viewModels {
        UserViewModelFactory((application as VaccinationApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthenticationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        preferences = SharedPreferencesHandler(this)
        val token = preferences.getUserToken()

        if (token == "") {
            loadLogin()
        } else {
            val mainIntent = Intent(this, MainActivity::class.java)
            startActivity(mainIntent)
        }
    }

    fun loadLogin() {
        supportFragmentManager.commit {
            replace<LoginFragment>(R.id.auth_fragment_container)
            setReorderingAllowed(true)
            addToBackStack("Login")
        }
    }

    fun loadRegister() {
        supportFragmentManager.commit {
            replace<RegisterFragment>(R.id.auth_fragment_container)
            setReorderingAllowed(true)
            addToBackStack("Register")
        }
    }

    fun login(email: String, password: String) {

        auth = AuthService.firebaseSingInWithEmail(email, password)
        auth.addOnCompleteListener(this) { task ->

            if (task.isSuccessful) {
                Toast.makeText(baseContext, "Authenticacion succesful",
                    Toast.LENGTH_SHORT).show()
                val mainIntent = Intent(this, MainActivity::class.java)
                startActivity(mainIntent)
                preferences.setUserToken("login")

            } else {
                Toast.makeText(baseContext, "Wrong email or pass", Toast.LENGTH_SHORT).show()
                loadLogin()
            }
        }
    }

    fun loginAnonymus(){
        auth = AuthService.firebaseSingInAnonymously()
        auth.addOnCompleteListener(this){ task ->
            if (task.isSuccessful) {
                val mainIntent = Intent(this, MainActivity::class.java)
                startActivity(mainIntent)
            } else {
                Toast.makeText(baseContext, "Authentication failed.",
                    Toast.LENGTH_SHORT).show()
                loadLogin()
            }
        }
    }

    fun register(user: User, email: String, password: String) {
        userViewModel.insert(user)
        auth = AuthService.firebaseRegister(email, password)

        auth.addOnCompleteListener(this) { task ->

            if(task.isSuccessful) {
                Toast.makeText(baseContext, "User created", Toast.LENGTH_SHORT).show()
                UserRepository.saveUser(user)
            } else {
                Toast.makeText(baseContext, "Something went wrong", Toast.LENGTH_SHORT).show()
                loadRegister()
            }
        }
        login(email, password)
    }
}