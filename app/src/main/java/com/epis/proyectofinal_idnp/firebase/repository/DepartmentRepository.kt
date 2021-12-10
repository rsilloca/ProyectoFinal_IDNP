package com.epis.proyectofinal_idnp.firebase.repository

import com.epis.proyectofinal_idnp.firebase.model.Department


object DepartmentRepository: FirebaseRepository<Department>(Department::class.java) {
}