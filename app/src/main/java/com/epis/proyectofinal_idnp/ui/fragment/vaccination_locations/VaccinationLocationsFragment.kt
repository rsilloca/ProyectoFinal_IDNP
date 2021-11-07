package com.epis.proyectofinal_idnp.ui.fragment.vaccination_locations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.epis.proyectofinal_idnp.databinding.FragmentVaccinationLocationsBinding

class VaccinationLocationsFragment : Fragment() {

    private lateinit var vaccinationLocationsViewModel: VaccinationLocationsViewModel
    private var _binding: FragmentVaccinationLocationsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        vaccinationLocationsViewModel =
                ViewModelProvider(this).get(VaccinationLocationsViewModel::class.java)

        _binding = FragmentVaccinationLocationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textGallery
        vaccinationLocationsViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}