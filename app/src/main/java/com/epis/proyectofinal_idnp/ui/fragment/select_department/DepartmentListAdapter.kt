package com.epis.proyectofinal_idnp.ui.fragment.select_department

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.TextView
import com.epis.proyectofinal_idnp.R
import com.epis.proyectofinal_idnp.firebase.model.Department
import java.util.*

class DepartmentListAdapter (private val c: Context,
                    private val items: MutableList<Department>
)
    : ArrayAdapter<Department>(c, 0, items) {

    var filteredDepartment: List<Department> = listOf()

    override fun getCount(): Int = filteredDepartment.size

    override fun getItem(position: Int): Department = filteredDepartment[position]

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(c).inflate(R.layout.autocompletetextview_item, parent, false)

        view.findViewById<TextView>(R.id.tvProcode).text =
            filteredDepartment[position].id_departamento.toString()

        view.findViewById<TextView>(R.id.tvProname).text = filteredDepartment[position].departamento

        return view
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun publishResults(charSequence: CharSequence?, filterResults: FilterResults) {
                @Suppress("UNCHECKED_CAST")
                filteredDepartment = filterResults.values as List<Department>
                notifyDataSetChanged()
            }

            override fun performFiltering(charSequence: CharSequence?): FilterResults {
                val queryString = charSequence?.toString()?.lowercase(Locale.getDefault())

                val filterResults = FilterResults()
                filterResults.values = if (queryString == null || queryString.isEmpty())
                    items
                else
                    items.filter {

                        it.departamento.lowercase(Locale.ROOT).contains(queryString)
                    }

                return filterResults
            }
        }
    }

}