package com.epis.proyectofinal_idnp.ui.fragment.reminder

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.provider.CalendarContract
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.epis.proyectofinal_idnp.databinding.FragmentReminderBinding
import com.epis.proyectofinal_idnp.firebase.model.VaccinationEvent
import com.epis.proyectofinal_idnp.utils.SharedPreferencesHandler
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

class ReminderFragment : Fragment() {

    private lateinit var reminderViewModel: ReminderViewModel
    private lateinit var preferences: SharedPreferencesHandler
    private var _binding: FragmentReminderBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var months = arrayOf(
        "Ene", "Feb", "Mar", "Abr", "May", "Jun",
        "Jul", "Ago", "Sep", "Oct", "Nov", "Dic"
    )
    private var dateReminder = Date()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        reminderViewModel =
                ViewModelProvider(this).get(ReminderViewModel::class.java)

        _binding = FragmentReminderBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val etDate = binding.selectDate
        etDate.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)
            val dpd = DatePickerDialog(requireContext(), { _, year, monthOfYear, dayOfMonth ->
                etDate.setText("$dayOfMonth ${months[monthOfYear]}, $year")
                dateReminder = Date(year, monthOfYear, dayOfMonth)
            }, year, month, day)
            dpd.show()
        }

        val dpTime = binding.pickerHour

        val btnAddReminder = binding.actBtnReminder
        btnAddReminder.setOnClickListener {
            val startStr = "${dateReminder.year}-${dateReminder.month + 1}-${dateReminder.date} ${dpTime.hour}:${dpTime.minute}:00"
            val endStr = "${dateReminder.year}-${dateReminder.month + 1}-${dateReminder.date} ${dpTime.hour + 2}:${dpTime.minute}:00"
            val tsStart = Timestamp.valueOf(startStr)
            val tsEnd = Timestamp.valueOf(endStr)
            val eventIntent = Intent(Intent.ACTION_INSERT)

            preferences = context?.let { SharedPreferencesHandler(it) }!!
            val event = VaccinationEvent(
                preferences.getDepartment(),
                preferences.getProvince(),
                tsStart.time
            )

            reminderViewModel.saveVaccinationEvent(event)

            eventIntent.data = CalendarContract.Events.CONTENT_URI
            eventIntent.putExtra(CalendarContract.Events.TITLE, "Vacunaci??n")
            eventIntent.putExtra(CalendarContract.Events.DESCRIPTION, "Description")
            eventIntent.putExtra(CalendarContract.Events.EVENT_LOCATION, "Location")
            eventIntent.putExtra(CalendarContract.Events.ALL_DAY, false)
            eventIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, tsStart.time)
            eventIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, tsEnd.time)
            if (eventIntent.resolveActivity(requireActivity().packageManager) != null) {
                startActivity(eventIntent)
            } else {
                Toast.makeText(requireActivity(), "There is no app that can support this action", Toast.LENGTH_LONG).show()
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}