package com.epis.proyectofinal_idnp.ui.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import androidx.recyclerview.widget.RecyclerView
import com.epis.proyectofinal_idnp.R
import com.epis.proyectofinal_idnp.data.model.VaccinationLocation

class VaccinationLocationAdapter(
    private val locations: List<VaccinationLocation>,
    private val clickListener: (VaccinationLocation) -> Unit
) : RecyclerView.Adapter<VaccinationLocationAdapter.ViewHolder>() {

    private val colorGreen: Int = Color.rgb(153, 193, 185)
    private val colorOrange: Int = Color.rgb( 242, 208, 169)
    private val colorRed: Int = Color.rgb(216, 140, 154)

    inner class ViewHolder(itemView: View, clickAtPosition: (Int) -> Unit) : RecyclerView.ViewHolder(itemView) {
        val date: TextView = itemView.findViewById(R.id.date_card)
        val title: TextView = itemView.findViewById(R.id.title_card)
        val subtitle: TextView = itemView.findViewById(R.id.subtitle_card)
        val icon: ImageButton = itemView.findViewById(R.id.icon_card)
        val cardContent: RelativeLayout = itemView.findViewById(R.id.card_content)

        init {
            itemView.setOnClickListener {
                clickAtPosition(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val vaccinationLocationView = inflater.inflate(R.layout.item_vaccination_location, parent, false)
        return ViewHolder(vaccinationLocationView) {
            clickListener(locations[it])
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val vaccinationLocation = locations[position]
        val date = holder.date
        date.text = vaccinationLocation.date
        val title = holder.title
        title.text = vaccinationLocation.title
        val subtitle = holder.subtitle
        subtitle.text = vaccinationLocation.subtitle
        val icon = holder.icon
        val cardContent = holder.cardContent
        var colorSelected = when (position % 3) {
            0 -> {
                colorGreen
            }
            1 -> {
                colorOrange
            }
            else -> {
                colorRed
            }
        }
        cardContent.setBackgroundColor(Color.argb(
            25,
            colorSelected.red,
            colorSelected.green,
            colorSelected.blue
        ))
        title.setTextColor(colorSelected)
        subtitle.setTextColor(colorSelected)
        icon.setColorFilter(colorSelected)
        icon.setOnClickListener {
            clickListener(locations[position])
        }
    }

    override fun getItemCount(): Int {
        return locations.size
    }

}