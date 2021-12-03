package com.epis.proyectofinal_idnp.ui.fragment.select_department

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.TextView
import com.epis.proyectofinal_idnp.R
import com.epis.proyectofinal_idnp.firebase.model.Province
import java.util.*

class ProvinceListAdapter (private val c: Context,
                           private val items: MutableList<Province>
)
    : ArrayAdapter<Province>(c, 0, items) {

    var filteredProvince: List<Province> = listOf()

    override fun getCount(): Int = filteredProvince.size

    override fun getItem(position: Int): Province = filteredProvince[position]

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(c).inflate(R.layout.autocompletetextview_item, parent, false)

        view.findViewById<TextView>(R.id.tvProcode).text =
            filteredProvince[position].id_provincia.toString()

        view.findViewById<TextView>(R.id.tvProname).text = filteredProvince[position].provincia

        return view
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun publishResults(charSequence: CharSequence?, filterResults: FilterResults) {
                @Suppress("UNCHECKED_CAST")
                filteredProvince = filterResults.values as List<Province>
                notifyDataSetChanged()
            }

            override fun performFiltering(charSequence: CharSequence?): FilterResults {
                val queryString = charSequence?.toString()?.lowercase(Locale.getDefault())

                val filterResults = FilterResults()
                filterResults.values = if (queryString == null || queryString.isEmpty())
                    items
                else
                    items.filter {

                        it.provincia.lowercase(Locale.ROOT)!!.contains(queryString)
                    }

                return filterResults
            }
        }
    }

}