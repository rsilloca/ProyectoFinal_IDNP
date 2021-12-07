package com.epis.proyectofinal_idnp.ui.fragment.select_department

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.epis.proyectofinal_idnp.databinding.FragmentSelectDepartmentBinding
import com.epis.proyectofinal_idnp.firebase.model.Department
import com.epis.proyectofinal_idnp.firebase.model.Province
//import com.epis.proyectofinal_idnp.firebase.model.GenericDP
import com.epis.proyectofinal_idnp.ui.activity.main.MainActivity
import com.epis.proyectofinal_idnp.ui.adapter.DepartmentListAdapter
import com.epis.proyectofinal_idnp.ui.adapter.ProvinceListAdapter

class SelectDepartmentFragment : Fragment() {
    private lateinit var selectDepartmentViewModel: SelectDepartmentViewModel
    private var _binding: FragmentSelectDepartmentBinding? = null
    private lateinit var departmentList: MutableList<Department>
    private lateinit var provinceList: MutableList<Province>

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = SelectDepartmentFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        selectDepartmentViewModel =
            ViewModelProvider(this).get(SelectDepartmentViewModel::class.java)

        _binding = FragmentSelectDepartmentBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val selectDepartmentBtn = binding.nextSelectDepartment
        selectDepartmentBtn.setOnClickListener {
            val departmentSelected = departmentList.find { d -> d.departamento.equals(binding.autocompleteDepartment.text.toString(), true) }
            val provinceSelected = provinceList.find { p -> p.provincia.equals(binding.autocompleteProvince.text.toString(), true) }
            if (departmentSelected != null && provinceSelected != null) {
                (activity as MainActivity).goSelectLocation(departmentSelected.id_departamento, provinceSelected.id_provincia)
            }
        }

        autoCompleteDep(inflater)

        binding.autocompleteDepartment.setOnItemClickListener{ parent, _, position, _ ->
            val selectedItem = parent.getItemAtPosition(position) as Department
            binding.autocompleteDepartment.setText(selectedItem.departamento)
            autoCompletePro(inflater, selectedItem)
        }

        binding.autocompleteProvince.setOnItemClickListener{ parent, _, position, _ ->
            val selectedItem = parent.getItemAtPosition(position) as Province
            binding.autocompleteProvince.setText(selectedItem.provincia)
        }

        return root
    }

    private fun autoCompleteDep(inflater: LayoutInflater){
        departmentList = mutableListOf()
        val department = selectDepartmentViewModel
        department.getAllDepartmentListLiveData()?.observe(viewLifecycleOwner, { departments ->
            departments?.forEach{
                departmentList += it
            }
        })
        val departmentAdapter = DepartmentListAdapter(inflater.context, departmentList)
        binding.autocompleteDepartment.setAdapter(departmentAdapter)

    }

    private fun autoCompletePro(inflater: LayoutInflater, department:Department){
        binding.autocompleteProvince.setAdapter(null)
        provinceList = mutableListOf()
        val province = selectDepartmentViewModel

        province.getAllProvinceListLiveData(department.id_departamento)?.observe(
            viewLifecycleOwner, { provinces ->
                provinces?.forEach{
                    provinceList += it
                }
            }
        )
        val provinceAdapter = ProvinceListAdapter(inflater.context, provinceList)
        binding.autocompleteProvince.setAdapter(provinceAdapter)
    }
}