package com.epis.proyectofinal_idnp.ui.fragment.statistics

import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.epis.proyectofinal_idnp.R
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
    private lateinit var barChart: BarChart
    private var startDate: Date = Date()
    private var endDate: Date = Date()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        statisticsViewModel =
                ViewModelProvider(this).get(StatisticsViewModel::class.java)

        _binding = FragmentStatisticsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        barChart = binding.barChart
        barChart.setColor(requireActivity().getColor(R.color.primary))
        val nightModeFlag = requireContext().resources.configuration.uiMode and
                Configuration.UI_MODE_NIGHT_MASK
        if (nightModeFlag == Configuration.UI_MODE_NIGHT_YES) {
            barChart.setLabelColor(Color.WHITE)
        }

        fillEventosList()

        return root
    }

    private fun fillEventosList(){
        allEvents = mutableListOf()
        val viewModelStatics = statisticsViewModel
        startDate = Date() // 1639546516209 -> Actual Date and Hour
        startDate.date -= startDate.day
        startDate.hours = 0
        startDate.minutes = 0
        Log.e("start date", startDate.toString())
        endDate = Date(startDate.time) // Thu Dec 16 23:47:00 GMT-05:00 2021
        endDate.date += 6
        endDate.hours = 23
        endDate.minutes = 59
        Log.e("end date", endDate.toString())
        viewModelStatics.finAllEventsBetween(startDate.time, endDate.time)?.observe(viewLifecycleOwner, { events ->
            var index = 0
            events?.forEach{
                allEvents += it
                Log.e("TAG", it.toString() + " " + index.toString() + " " + events.size)
                index += 1
            }
            plotChart(7)
        })
    }

    private fun plotChart(days: Int) {
        val startDay = Date(startDate.time)
        val endDay = Date(startDay.time)
        endDay.hours = 23
        endDay.minutes = 59
        for (i in 1..days) {
            val bar = BarChartItem("${startDay.date}/${startDay.month + 1}", allEvents.filter {
                    e -> e.dateEvent >= startDay.time && e.dateEvent <= endDay.time
            }.toList().size)
            Log.e(startDay.toString(), bar.y.toString())
            barChart.addBar(bar)
            startDay.date += 1
            endDay.date += 1
        }
        barChart.drawBarChart()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}