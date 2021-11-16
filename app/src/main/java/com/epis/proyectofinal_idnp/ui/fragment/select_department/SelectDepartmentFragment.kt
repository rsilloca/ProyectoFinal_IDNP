package com.epis.proyectofinal_idnp.ui.fragment.select_department

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.epis.proyectofinal_idnp.R
import com.epis.proyectofinal_idnp.databinding.FragmentHomeBinding
import com.epis.proyectofinal_idnp.databinding.FragmentSelectDepartmentBinding
import com.epis.proyectofinal_idnp.ui.activity.main.MainActivity
import com.epis.proyectofinal_idnp.ui.fragment.home.HomeViewModel

class SelectDepartmentFragment : Fragment() {

    private lateinit var selectDepartmentViewModel: SelectDepartmentViewModel
    private var _binding: FragmentSelectDepartmentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = SelectDepartmentFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        selectDepartmentViewModel =
            ViewModelProvider(this).get(SelectDepartmentViewModel::class.java)

        _binding = FragmentSelectDepartmentBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val selectDepartmentBtn = binding.nextSelectDepartment
        selectDepartmentBtn.setOnClickListener {
            (activity as MainActivity).goSelectLocation()
        }
        return root
    }

}