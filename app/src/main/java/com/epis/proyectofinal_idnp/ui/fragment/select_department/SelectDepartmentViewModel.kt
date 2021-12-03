package com.epis.proyectofinal_idnp.ui.fragment.select_department

import androidx.lifecycle.ViewModel
import com.epis.proyectofinal_idnp.firebase.repository.DepartmentRepository
import com.epis.proyectofinal_idnp.firebase.livedata.MultipleDocumentReferenceLiveData
import com.epis.proyectofinal_idnp.firebase.model.Department
import com.epis.proyectofinal_idnp.firebase.model.Province
import com.epis.proyectofinal_idnp.firebase.repository.ProvinceRepository
import com.google.firebase.firestore.Query

class SelectDepartmentViewModel() : ViewModel() {
    private var departmentRepository = DepartmentRepository
    private var provinceRepository = ProvinceRepository
    private var allLiveDataDep: MultipleDocumentReferenceLiveData<Department, out Query?>? = null
    private var allLiveDataProv: MultipleDocumentReferenceLiveData<Province, out Query?>? = null


    fun getAllDepartmentListLiveData(): MultipleDocumentReferenceLiveData<Department, out Query?>? {
        if (allLiveDataDep == null) {
            allLiveDataDep = departmentRepository.findAll()
        }
        return allLiveDataDep
    }

    fun getAllProvinceListLiveData(idDepartment:Int): MultipleDocumentReferenceLiveData<Province, out Query?>? {
        //if (allLiveDataProv != null) {
            allLiveDataProv = provinceRepository.findByIdDepartment(idDepartment)
        //}
        return allLiveDataProv
    }

}