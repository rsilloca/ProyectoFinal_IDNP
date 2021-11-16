package com.epis.proyectofinal_idnp.data.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.epis.proyectofinal_idnp.data.dao.UserDao
import com.epis.proyectofinal_idnp.data.model.User


class UserRepository(private val userDao: UserDao) {

    val getAllUsers: LiveData<List<User>> = userDao.getAllUser()

    @WorkerThread
    suspend fun getUserById(idUser: Int): LiveData<User> = userDao.getUser(idUser)

    @WorkerThread
    suspend fun insertUser(user: User) = userDao.insertUser(user)

    @WorkerThread
    suspend fun updateUser(vararg user: User) = userDao.updateUser(*user)

    @WorkerThread
    suspend fun deleteUser(idUser: Int) = userDao.deleteUser(idUser)
}