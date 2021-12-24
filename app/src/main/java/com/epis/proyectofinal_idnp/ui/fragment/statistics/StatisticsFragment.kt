package com.epis.proyectofinal_idnp.ui.fragment.statistics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.epis.proyectofinal_idnp.R
import com.epis.proyectofinal_idnp.data.statistics.BarChart
import com.epis.proyectofinal_idnp.databinding.FragmentStatisticsBinding

class StatisticsFragment : Fragment() {

    private lateinit var statisticsViewModel: StatisticsViewModel
    private var _binding: FragmentStatisticsBinding? = null
    private var months = arrayOf(
        "Ene", "Feb", "Mar", "Abr", "May", "Jun",
        "Jul", "Ago", "Sep", "Oct", "Nov", "Dic"
    )
    private var days = arrayOf(
        "Lun", "Mar", "Mie", "Jue", "Vie", "Sáb", "Dom"
    )

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

        val barChart = BarChart(container!!.context)
        val relativeLayout: RelativeLayout = binding.barChart
        relativeLayout.addView(barChart)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}