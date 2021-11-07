package com.epis.proyectofinal_idnp.ui.fragment.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RegisterViewModel : ViewModel() {

    private val _visibility = MutableLiveData<Boolean>().apply {
        value = true
    }
    val visibility: LiveData<Boolean> = _visibility

    fun toggleVisibility() {
        _visibility.value = !_visibility.value!!
    }

}