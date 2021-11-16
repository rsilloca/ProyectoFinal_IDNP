package com.epis.proyectofinal_idnp.viewmodel

import android.app.Application
import com.epis.proyectofinal_idnp.data.DatabaseConfig
import com.epis.proyectofinal_idnp.data.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class UserApplication : Application() {

    val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy { DatabaseConfig.getDatabase(this) }
    val repository by lazy { UserRepository(database.userDao()) }

}