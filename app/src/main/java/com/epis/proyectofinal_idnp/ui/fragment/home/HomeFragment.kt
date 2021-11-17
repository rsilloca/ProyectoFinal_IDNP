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
import com.epis.proyectofinal_idnp.ui.activity.auth.AuthenticationActivity
import com.epis.proyectofinal_idnp.ui.activity.main.MainActivity
import com.epis.proyectofinal_idnp.viewmodel.UserViewModel
import com.epis.proyectofinal_idnp.viewmodel.UserViewModelFactory

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    private val userViewModel: UserViewModel by viewModels {
        UserViewModelFactory((this.activity?.application as VaccinationApplication).repository)
    }

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

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

        Log.e("Usuarios HomeF", userViewModel.getAllUsers.value.toString())

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}