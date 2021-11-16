package com.epis.proyectofinal_idnp.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.epis.proyectofinal_idnp.data.model.User

@Dao
abstract class UserDao {

    @Query("SELECT * FROM User")
    abstract fun getAllUser(): LiveData<List<User>>

    @Query("SELECT * FROM User WHERE id = :idUser")
    abstract fun getUser(idUser: Int): LiveData<User>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insertUser(user: User)

    @Update
    abstract fun updateUser(vararg user: User)

    @Query("DELETE FROM User WHERE id = :idUser")
    abstract fun deleteUser(idUser: Int)

    @Query("DELETE FROM User")
    abstract fun deleteAllUsers()
}