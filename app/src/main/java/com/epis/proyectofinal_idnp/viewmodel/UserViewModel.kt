package com.epis.proyectofinal_idnp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.epis.proyectofinal_idnp.data.model.User
import com.epis.proyectofinal_idnp.data.repository.UserRepository
import kotlinx.coroutines.launch

class UserViewModel(private val userRepository: UserRepository) : ViewModel() {

    val getAllUsers: LiveData<List<User>> = userRepository.getAllUsers

    fun insert(user: User) = viewModelScope.launch { userRepository.insertUser(user) }

    fun findById(id: Int) = viewModelScope.launch { userRepository.getUserById(id) }

    fun update(user: User) = viewModelScope.launch { userRepository.updateUser(user) }

    fun delete(id: Int) = viewModelScope.launch { userRepository.deleteUser(id) }
}

class UserViewModelFactory(private val userRepository: UserRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UserViewModel(userRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}