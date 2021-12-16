package com.epis.proyectofinal_idnp.ui.fragment.favorites

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.epis.proyectofinal_idnp.R
import com.epis.proyectofinal_idnp.data.model.VaccinationLocation
import com.epis.proyectofinal_idnp.databinding.FragmentFavoritesBinding
import com.epis.proyectofinal_idnp.firebase.model.FavoritesVaccionationLocal
import com.epis.proyectofinal_idnp.firebase.model.VaccinationEvent
import com.epis.proyectofinal_idnp.firebase.model.VaccinationLocal
import com.epis.proyectofinal_idnp.ui.activity.main.MainActivity
import com.epis.proyectofinal_idnp.ui.adapter.VaccinationLocationAdapter

class FavoritesFragment : Fragment() {

    private lateinit var favoritesViewModel: FavoritesViewModel
    private var _binding: FragmentFavoritesBinding? = null
    private lateinit var listLocal: MutableList<VaccinationLocation>
    private lateinit var favs: MutableList<FavoritesVaccionationLocal>
    private lateinit var list: MutableList<VaccinationLocal>
    private lateinit var adapter: VaccinationLocationAdapter


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        favoritesViewModel =
                ViewModelProvider(this).get(FavoritesViewModel::class.java)

        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val recyclerView = binding.vaccinationLocationsRv

        listVaccinationLocals(recyclerView)

        return root
    }


    private fun fillAdapter(recyclerView: RecyclerView, local: MutableList<VaccinationLocation>){
        Log.e("local data", local.size.toString())
        adapter = VaccinationLocationAdapter(local) {
            Log.e("TAG", it.toString())
            Log.d("click event", it.subtitle)
            showDialogActions(it)
        }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
    }

    private fun listVaccinationLocals(recyclerView:RecyclerView){
        val favoritesViewModel = favoritesViewModel
        listLocal = mutableListOf()
        fillAdapter(recyclerView, listLocal)
        favs = mutableListOf()
        list = mutableListOf()
        favoritesViewModel.getAllFavoritesLocals()?.observe(viewLifecycleOwner, { local ->
            Log.e("receive observe", local?.size.toString())
            local?.forEach{ fav->
                favoritesViewModel.getLocalVaccination(fav.id_local)?.observe(viewLifecycleOwner,{ ml ->
                    list += ml
                    Log.e("TAG", ml.toString())
                    listLocal += VaccinationLocation(ml.documentId!!, "Inicia el 17 de Dic",
                        ml.nombre, ml.distrito, ml.id_departamento, ml.id_provincia,
                        ml.latitud, ml.longitud)
                    Log.e("TAG", ml.toString())
                    adapter.notifyDataSetChanged()
                })
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showDialogActions(location: VaccinationLocation) {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_location_actions)

        val btnComoLlegar = dialog.findViewById<Button>(R.id.btn_how_to_get)
        val btnClose = dialog.findViewById<Button>(R.id.btn_close_dialog)
        val btnFavorite = dialog.findViewById<ImageButton>(R.id.icon_favorite)
        val title = dialog.findViewById<TextView>(R.id.dialog_title)
        val name = dialog.findViewById<TextView>(R.id.dialog_place)
        val date = dialog.findViewById<TextView>(R.id.dialog_date)

        title.text = location.title
        name.text = location.subtitle
        date.text = location.date

        btnFavorite.setImageDrawable(requireActivity().getDrawable(R.drawable.ic_favorite))

        btnComoLlegar.setOnClickListener {
            (activity as MainActivity).viewRoute(location)
            dialog.dismiss()
        }

        btnClose.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.attributes?.windowAnimations = R.style.dialog_animation
        dialog.window?.setGravity(Gravity.BOTTOM)
    }
}