package com.epis.proyectofinal_idnp.ui.fragment.vaccination_locations

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.epis.proyectofinal_idnp.R
import com.epis.proyectofinal_idnp.data.model.VaccinationLocation
import com.epis.proyectofinal_idnp.firebase.model.VaccinationLocal
import com.epis.proyectofinal_idnp.databinding.FragmentVaccinationLocationsBinding
import com.epis.proyectofinal_idnp.ui.adapter.VaccinationLocationAdapter
import com.epis.proyectofinal_idnp.ui.fragment.draw_path.DrawPathFragment
import com.epis.proyectofinal_idnp.ui.fragment.draw_path.DrawPathViewModel
import org.w3c.dom.Text


class VaccinationLocationsFragment : Fragment() {

    private lateinit var vaccinationLocationsViewModel: VaccinationLocationsViewModel
    private var _binding: FragmentVaccinationLocationsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var model: DrawPathViewModel? = null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        vaccinationLocationsViewModel =
                ViewModelProvider(this).get(VaccinationLocationsViewModel::class.java)

        _binding = FragmentVaccinationLocationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val recyclerView = binding.vaccinationLocationsRv
        var locations = mutableListOf<VaccinationLocation>(
            VaccinationLocation(
                "Inicia el 21 DIC",
                "CERCADO (18 - 28 años)",
                "Estadio de la UNSA"
            ),
            VaccinationLocation(
                "Inicia el 21 DIC",
                "CERCADO (18 - 28 años)",
                "I. E. Juana Cervantes de Bolognesi..."
            ),
            VaccinationLocation(
                "Inicia el 21 DIC",
                "CERCADO (18 - 28 años)",
                "Centro Comercial La Salle"
            ),
            VaccinationLocation(
                "Inicia el 22 DIC",
                "CERCADO (18 - 28 años)",
                "Complejo Rayo Chachani (Calle..."
            ),
            VaccinationLocation(
                "Inicia el 22 DIC",
                "CERCADO (18 - 28 años)",
                "I. E. 41026 Maria Murillo de Bernal..."
            )
        )

        // Lista de locales de firebase
        val listLocal = mutableListOf<VaccinationLocal>()
        vaccinationLocationsViewModel.getAllVaccionationLocalListLiveData()?.observe(
            viewLifecycleOwner, { vaccionationLocalList ->
                vaccionationLocalList?.forEach{
                    listLocal += it
                }
            }
        )

        val adapter = VaccinationLocationAdapter(locations) {
            Log.d("click event", it.subtitle)
            showDialogActions(it)
        }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        return root
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
        val title = dialog.findViewById<TextView>(R.id.dialog_title)
        val name = dialog.findViewById<TextView>(R.id.dialog_place)
        val date = dialog.findViewById<TextView>(R.id.dialog_date)

        title.text = location.title
        name.text = location.subtitle
        date.text = location.date

        btnComoLlegar.setOnClickListener {
            val model = ViewModelProvider(this).get(DrawPathViewModel::class.java)
            model?.setLocation(location.latitude, location.longitude)
            val myfragment = DrawPathFragment()
            val fragmentTransaction = requireFragmentManager().beginTransaction()
            fragmentTransaction.replace(R.id.nav_host_fragment_content_main, myfragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
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