package com.epis.proyectofinal_idnp.ui.activity.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.epis.proyectofinal_idnp.R
import com.epis.proyectofinal_idnp.data.application.VaccinationApplication
import com.epis.proyectofinal_idnp.data.model.User
import com.epis.proyectofinal_idnp.databinding.ActivityAuthenticationBinding
import com.epis.proyectofinal_idnp.ui.activity.main.MainActivity
import com.epis.proyectofinal_idnp.ui.fragment.login.LoginFragment
import com.epis.proyectofinal_idnp.ui.fragment.register.RegisterFragment
import com.epis.proyectofinal_idnp.utils.SharedPreferencesHandler
import com.epis.proyectofinal_idnp.viewmodel.UserViewModel
import com.epis.proyectofinal_idnp.viewmodel.UserViewModelFactory

class AuthenticationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthenticationBinding
    private lateinit var preferences: SharedPreferencesHandler
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
            login()
        }

        Log.e("Usuarios Activ", userViewModel.getAllUsers.value.toString())

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

    fun login() {
        preferences.setUserToken("login")
        val mainIntent = Intent(this, MainActivity::class.java)
        startActivity(mainIntent)
    }

    fun register(user: User) {
        userViewModel.insert(user)
        login()
    }
}