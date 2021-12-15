package com.epis.proyectofinal_idnp.ui.fragment.statistics

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.epis.proyectofinal_idnp.databinding.FragmentStatisticsBinding
import com.epis.proyectofinal_idnp.firebase.model.VaccinationEvent
import java.util.*

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

        fillEventosList()

        return root
    }

    private fun fillEventosList(){
        allEvents = mutableListOf()
        val viewModelStatics = statisticsViewModel
        val d1 = Date() // 1639546516209 -> Actual Date and Hour
        val d2 = 1639716420000 // Thu Dec 16 23:47:00 GMT-05:00 2021
        viewModelStatics.finAllEventsBetween(d1.time, d2)?.observe(viewLifecycleOwner, { events ->
            events?.forEach{
                allEvents += it
                Log.e("TAG", ""+it.toString())
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}