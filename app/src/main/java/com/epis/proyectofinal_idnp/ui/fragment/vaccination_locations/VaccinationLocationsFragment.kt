package com.epis.proyectofinal_idnp.ui.fragment.vaccination_locations

import android.R.attr
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.epis.proyectofinal_idnp.data.model.VaccinationLocation
import com.epis.proyectofinal_idnp.databinding.FragmentVaccinationLocationsBinding
import com.epis.proyectofinal_idnp.ui.adapter.VaccinationLocationAdapter
import android.R.attr.numColumns




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

        val recyclerView = binding.vaccinationLocationsRv
        var locations = mutableListOf<VaccinationLocation>(
            VaccinationLocation(
                "Inicia el 21 DIC",
                "CERCADO (18 - 28 años)",
                "Estadio de la UNSA"
            ),
            VaccinationLocation(
                "Inicia el 21 DIC",
                "CERCADO (18 - 28 años)",
                "I. E. Juana Cervantes de Bolognesi..."
            ),
            VaccinationLocation(
                "Inicia el 21 DIC",
                "CERCADO (18 - 28 años)",
                "Centro Comercial La Salle"
            ),
            VaccinationLocation(
                "Inicia el 22 DIC",
                "CERCADO (18 - 28 años)",
                "Complejo Rayo Chachani (Calle..."
            ),
            VaccinationLocation(
                "Inicia el 22 DIC",
                "CERCADO (18 - 28 años)",
                "I. E. 41026 Maria Murillo de Bernal..."
            )
        )

        val adapter = VaccinationLocationAdapter(locations)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}