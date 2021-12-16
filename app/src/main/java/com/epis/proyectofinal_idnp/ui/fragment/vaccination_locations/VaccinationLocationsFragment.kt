package com.epis.proyectofinal_idnp.ui.fragment.vaccination_locations

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
import com.epis.proyectofinal_idnp.databinding.FragmentVaccinationLocationsBinding
import com.epis.proyectofinal_idnp.firebase.model.VaccinationLocal
import com.epis.proyectofinal_idnp.ui.activity.main.MainActivity
import com.epis.proyectofinal_idnp.ui.adapter.VaccinationLocalListAdapter
import com.epis.proyectofinal_idnp.ui.adapter.VaccinationLocationAdapter
import com.epis.proyectofinal_idnp.utils.SharedPreferencesHandler
import androidx.lifecycle.Observer
import com.epis.proyectofinal_idnp.firebase.model.FavoritesVaccionationLocal


class VaccinationLocationsFragment : Fragment() {

    private lateinit var vaccinationLocationsViewModel: VaccinationLocationsViewModel
    private lateinit var preferences: SharedPreferencesHandler
    private lateinit var listlocalF: MutableList<VaccinationLocal>
    private lateinit var listLocal: MutableList<VaccinationLocation>
    private lateinit var fecha:String
    private var _binding: FragmentVaccinationLocationsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var idDepartment: Int = 1
    private var idProvince: Int = 1
    private lateinit var favs: List<FavoritesVaccionationLocal>

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        vaccinationLocationsViewModel =
                ViewModelProvider(this).get(VaccinationLocationsViewModel::class.java)

        _binding = FragmentVaccinationLocationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        preferences = context?.let { SharedPreferencesHandler(it) }!!
        idDepartment = preferences.getDepartment()//arguments?.getInt("idDepartment") ?: idDepartment
        idProvince = preferences.getProvince()//arguments?.getInt("idProvince") ?: idProvince
        Log.e("Department", idDepartment.toString())
        Log.e("Province", idProvince.toString())

        val recyclerView = binding.vaccinationLocationsRv
        fecha = ""
        vaccinationLocationsViewModel.getDate()?.observe(viewLifecycleOwner, {
            fecha = "Inicia el " + it.fecha
        })

        listVaccinationLocals(recyclerView)
        listVaccinationLocalsFav()
        autoCompleteLocals(inflater)

        binding.autocompleteLocals.setOnItemClickListener{ parent, _, position, _ ->
            val selectedItem = parent.getItemAtPosition(position) as VaccinationLocal
            binding.autocompleteLocals.setText(selectedItem.distrito)
            filteredVacinationLocals(recyclerView, selectedItem.distrito)
        }

        return root
    }

    private fun fillAdapter(recyclerView:RecyclerView, local:MutableList<VaccinationLocation>){
        val adapter = VaccinationLocationAdapter(local) {
            Log.e("TAG", it.toString())
            Log.d("click event", it.subtitle)
            showDialogActions(it)
        }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
    }

    private fun listVaccinationLocals(recyclerView:RecyclerView){
        val vaccinationLocals = vaccinationLocationsViewModel
        listLocal = mutableListOf()
        vaccinationLocals.getAllVaccionationLocalListLiveDataByProvince(idProvince)?.observe(
            viewLifecycleOwner, { vaccionationLocalList ->
                vaccionationLocalList?.forEach{
                    listLocal += VaccinationLocation(it.documentId!!, fecha,
                        it.nombre, it.distrito, it.id_departamento, it.id_provincia,
                        it.latitud, it.longitud)
                }
                fillAdapter(recyclerView, listLocal)
            }
        )
    }

    private fun filteredVacinationLocals(recyclerView:RecyclerView, distrite: String){
        val vaccinationLocals = vaccinationLocationsViewModel
        listLocal = mutableListOf()
        vaccinationLocals.getAllVaccionationLocalListLiveDataByDistrite(distrite)?.observe(
            viewLifecycleOwner, { vaccionationLocalList ->
                vaccionationLocalList?.forEach{
                    listLocal += VaccinationLocation(it.documentId!!, fecha,
                        it.nombre, it.distrito, it.id_departamento, it.id_provincia,
                        it.latitud, it.longitud)
                }
                fillAdapter(recyclerView, listLocal)
            }
        )
    }

    private fun autoCompleteLocals(inflater: LayoutInflater){
        val vaccinationLocals = vaccinationLocationsViewModel
        listlocalF = mutableListOf()
        vaccinationLocals.getAllVaccionationLocalListLiveDataByProvince(idProvince)?.observe(
            viewLifecycleOwner, { vaccionationLocalList ->
                vaccionationLocalList?.forEach{
                    listlocalF += it
                }
            }
        )
        val vaccinationLocalListAdapter = VaccinationLocalListAdapter(inflater.context, listlocalF)
        binding.autocompleteLocals.setAdapter(vaccinationLocalListAdapter)
    }

    private fun listVaccinationLocalsFav(){
        val favoritesViewModel = vaccinationLocationsViewModel
        favs = mutableListOf()
        favoritesViewModel.getAllFavoritesLocals()?.observe(viewLifecycleOwner, { local ->
            if (local != null) {
                favs = local
            }
        })
    }

    // Sólo es necesario mandar el id del local
    private fun saveFavVaccinationLocal(idLocal: String) {
        Log.e("saving", "favorite")
        val vaccinationLocals = vaccinationLocationsViewModel
        vaccinationLocals.saveFavoriteLocalVaccination(idLocal)
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

        val fav = favs.find { f -> f.id_local == location.id }
        if (fav != null) {
            btnFavorite.setImageDrawable(requireActivity().getDrawable(R.drawable.ic_favorite))
        }

        btnComoLlegar.setOnClickListener {
            (activity as MainActivity).viewRoute(location)
            dialog.dismiss()
        }

        btnClose.setOnClickListener {
            dialog.dismiss()
        }

        if (fav == null) {
            btnFavorite.setOnClickListener {
                saveFavVaccinationLocal(location.id)
                btnFavorite.setImageDrawable(requireActivity().getDrawable(R.drawable.ic_favorite))
                btnFavorite.setOnClickListener {  }
            }
        }

        dialog.show()
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.attributes?.windowAnimations = R.style.dialog_animation
        dialog.window?.setGravity(Gravity.BOTTOM)
    }
}