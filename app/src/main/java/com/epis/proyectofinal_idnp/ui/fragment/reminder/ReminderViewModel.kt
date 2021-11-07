package com.epis.proyectofinal_idnp.ui.fragment.reminder

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ReminderViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Reminder Fragment"
    }
    val text: LiveData<String> = _text
}