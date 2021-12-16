package com.epis.proyectofinal_idnp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.TextView
import com.epis.proyectofinal_idnp.R
import com.epis.proyectofinal_idnp.firebase.model.VaccinationLocal
import java.util.*

class VaccinationLocalListAdapter(private val c: Context,
                                  private val items: MutableList<VaccinationLocal>
)
: ArrayAdapter<VaccinationLocal>(c, 0, items) {

    var filteredVaccinationLocal: List<VaccinationLocal> = listOf()

    override fun getCount(): Int = filteredVaccinationLocal.size

    override fun getItem(position: Int): VaccinationLocal = filteredVaccinationLocal[position]

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(c).inflate(R.layout.autocompletetextview_item, parent, false)

        view.findViewById<TextView>(R.id.tvProcode).text =
            filteredVaccinationLocal[position].nombre

        view.findViewById<TextView>(R.id.tvProname).text =
            filteredVaccinationLocal[position].distrito

        return view
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun publishResults(charSequence: CharSequence?, filterResults: FilterResults) {
                @Suppress("UNCHECKED_CAST")
                filteredVaccinationLocal = filterResults.values as List<VaccinationLocal>
                notifyDataSetChanged()
            }

            override fun performFiltering(charSequence: CharSequence?): FilterResults {
                val queryString = charSequence?.toString()?.lowercase(Locale.getDefault())

                val filterResults = FilterResults()
                filterResults.values = if (queryString == null || queryString.isEmpty())
                    items
                else
                    items.filter {

                        it.distrito.lowercase(Locale.ROOT).contains(queryString)
                    }

                return filterResults
            }
        }
    }
}