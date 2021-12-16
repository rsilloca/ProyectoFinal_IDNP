package com.epis.proyectofinal_idnp.ui.fragment.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.epis.proyectofinal_idnp.data.application.VaccinationApplication
import com.epis.proyectofinal_idnp.databinding.FragmentHomeBinding
import com.epis.proyectofinal_idnp.firebase.model.VaccinationEvent
import com.epis.proyectofinal_idnp.ui.activity.auth.AuthenticationActivity
import com.epis.proyectofinal_idnp.ui.activity.main.MainActivity
import com.epis.proyectofinal_idnp.viewmodel.UserViewModel
import com.epis.proyectofinal_idnp.viewmodel.UserViewModelFactory
import java.util.*

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    private val userViewModel: UserViewModel by viewModels {
        UserViewModelFactory((this.activity?.application as VaccinationApplication).repository)
    }

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var allEvents: MutableList<VaccinationEvent>
    private lateinit var tvQuantity: TextView

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val selectDepartmentBtn = binding.seeMoreHome
        selectDepartmentBtn.setOnClickListener {
            (activity as MainActivity).goSelectDepartment()
        }

        tvQuantity = binding.quantityPeople

        Log.e("Usuarios HomeF", userViewModel.getAllUsers.value.toString())

        fillEventosList()

        return root
    }

    private fun fillEventosList(){
        allEvents = mutableListOf()
        val viewModelStatics = homeViewModel
        val startDate = Date()
        startDate.hours = 0
        startDate.minutes = 0
        Log.e("start date", startDate.toString())
        val endDate = Date(startDate.time)
        endDate.hours = 23
        endDate.minutes = 59
        Log.e("end date", endDate.toString())
        viewModelStatics.finAllEventsBetween(startDate.time, endDate.time)?.observe(viewLifecycleOwner, { events ->
            tvQuantity.text = events?.size.toString()
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}