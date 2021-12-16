package com.epis.proyectofinal_idnp.ui.fragment.vaccination_locations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.epis.proyectofinal_idnp.firebase.livedata.DocumentReferenceFirebaseLiveData
import com.epis.proyectofinal_idnp.firebase.livedata.MultipleDocumentReferenceLiveData
import com.epis.proyectofinal_idnp.firebase.model.FavoritesVaccionationLocal
import com.epis.proyectofinal_idnp.firebase.model.NextEvent
import com.epis.proyectofinal_idnp.firebase.model.VaccinationLocal
import com.epis.proyectofinal_idnp.firebase.repository.FavoritesVaccinationLocalRepository
import com.epis.proyectofinal_idnp.firebase.repository.NextEventRepository
import com.epis.proyectofinal_idnp.firebase.repository.VaccinationLocalRepository
import com.epis.proyectofinal_idnp.firebase.service.AuthService
import com.google.firebase.firestore.Query

class VaccinationLocationsViewModel : ViewModel() {

    private var vaccionationLocal = VaccinationLocalRepository
    private var nextEvent = NextEventRepository
    private var vaccinationLocaFav = FavoritesVaccinationLocalRepository
    private var currentUser = AuthService.firebaseGetCurrentUser()
    private var allLiveDataLocal: MultipleDocumentReferenceLiveData<VaccinationLocal, out Query?>? = null
    private var allLiveDataNextEvent: DocumentReferenceFirebaseLiveData<NextEvent>? = null

    private var favoritesVaccionationLocal = FavoritesVaccinationLocalRepository
    private var allLiveDataLocalFav: MultipleDocumentReferenceLiveData<FavoritesVaccionationLocal, out Query?>? = null

    private val _text = MutableLiveData<String>().apply {
        value = "This is Vaccination Locations Fragment"
    }
    val text: LiveData<String> = _text

    fun getAllVaccionationLocalListLiveData() : MultipleDocumentReferenceLiveData<VaccinationLocal, out Query?>? {
        if(allLiveDataLocal == null) {
            allLiveDataLocal = vaccionationLocal.findAll()
        }
        return allLiveDataLocal
    }

    fun getAllVaccionationLocalListLiveDataByProvince(idProvince: Int) : MultipleDocumentReferenceLiveData<VaccinationLocal, out Query?>? {
        //if(allLiveDataLocal == null) {
            allLiveDataLocal = vaccionationLocal.findbyIdProvince(idProvince)
        //}
        return allLiveDataLocal
    }

    fun getAllVaccionationLocalListLiveDataByDistrite(distrite: String) : MultipleDocumentReferenceLiveData<VaccinationLocal, out Query?>? {
        //if(allLiveDataLocal == null) {
        allLiveDataLocal = vaccionationLocal.findbyDistrite(distrite)
        //}
        return allLiveDataLocal
    }

    fun getDate() : DocumentReferenceFirebaseLiveData<NextEvent>? {
        if(allLiveDataNextEvent == null){
            allLiveDataNextEvent = nextEvent.findById("1")
        }
        return allLiveDataNextEvent
    }

    fun saveFavoriteLocalVaccination(vaccinationLocalId: String){
        vaccinationLocaFav.save(FavoritesVaccionationLocal(vaccinationLocalId, currentUser!!.uid))
    }

    fun getAllFavoritesLocals() : MultipleDocumentReferenceLiveData<FavoritesVaccionationLocal, out Query?>? {
        if(allLiveDataLocalFav == null) {
            allLiveDataLocalFav = favoritesVaccionationLocal.findByIdUser(currentUser!!.uid)
        }
        return allLiveDataLocalFav
    }

}