package com.epis.proyectofinal_idnp.data.application

import android.app.Application
import com.epis.proyectofinal_idnp.data.DatabaseConfig
import com.epis.proyectofinal_idnp.data.repository.UserRepository

class VaccinationApplication : Application() {
    val database by lazy { DatabaseConfig.getDatabase(this) }
    val repository by lazy { UserRepository(database.userDao()) }
}