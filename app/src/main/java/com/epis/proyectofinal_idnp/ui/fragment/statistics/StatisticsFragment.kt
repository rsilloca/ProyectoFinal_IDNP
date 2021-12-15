package com.epis.proyectofinal_idnp.ui.fragment.statistics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.epis.proyectofinal_idnp.databinding.FragmentStatisticsBinding
import com.epis.proyectofinal_idnp.firebase.model.VaccinationEvent

class StatisticsFragment : Fragment() {

    private lateinit var statisticsViewModel: StatisticsViewModel
    private lateinit var allEvents: MutableList<VaccinationEvent>
    private var _binding: FragmentStatisticsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        statisticsViewModel =
                ViewModelProvider(this).get(StatisticsViewModel::class.java)

        _binding = FragmentStatisticsBinding.inflate(inflater, container, false)
        val root: View = binding.root


        return root
    }

    private fun fillEventosList(){
        allEvents = mutableListOf()
        val viewModelStatics = statisticsViewModel
        viewModelStatics.getAllVaccinationEvent()?.observe(viewLifecycleOwner, { events ->
            events?.forEach{
                allEvents += it
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}