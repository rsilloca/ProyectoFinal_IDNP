package com.epis.proyectofinal_idnp.ui.fragment.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.epis.proyectofinal_idnp.firebase.livedata.DocumentReferenceFirebaseLiveData
import com.epis.proyectofinal_idnp.firebase.livedata.MultipleDocumentReferenceLiveData
import com.epis.proyectofinal_idnp.firebase.model.FavoritesVaccionationLocal
import com.epis.proyectofinal_idnp.firebase.model.User
import com.epis.proyectofinal_idnp.firebase.model.VaccinationLocal
import com.epis.proyectofinal_idnp.firebase.repository.FavoritesVaccinationLocalRepository
import com.epis.proyectofinal_idnp.firebase.repository.UserRepository
import com.epis.proyectofinal_idnp.firebase.repository.VaccinationLocalRepository
import com.epis.proyectofinal_idnp.firebase.service.AuthService
import com.google.firebase.firestore.Query

class FavoritesViewModel : ViewModel() {

    private var favoritesVaccionationLocal = FavoritesVaccinationLocalRepository
    private var allLiveDataLocal: MultipleDocumentReferenceLiveData<FavoritesVaccionationLocal, out Query?>? = null
    private var currentUser = AuthService.firebaseGetCurrentUser()
    private var userRepository = UserRepository
    private var vaccionationLocal = VaccinationLocalRepository
    private var liveData: DocumentReferenceFirebaseLiveData<User>? = null
    private var allLiveDataLocalV: DocumentReferenceFirebaseLiveData<VaccinationLocal>? = null

    private val _text = MutableLiveData<String>().apply {
        value = "This is Favorites Fragment"
    }
    val text: LiveData<String> = _text

    fun getCurrentUserData(): DocumentReferenceFirebaseLiveData<User>?{
        if (liveData == null) {
            liveData = currentUser?.let { userRepository.findById(it.uid) }
        }
        return liveData
    }

    fun getAllFavoritesLocals(idUser: String) : MultipleDocumentReferenceLiveData<FavoritesVaccionationLocal, out Query?>? {
        if(allLiveDataLocal == null) {
            allLiveDataLocal = favoritesVaccionationLocal.findByIdUser(idUser)
        }
        return allLiveDataLocal
    }

    fun getLocalVaccination(id: String): DocumentReferenceFirebaseLiveData<VaccinationLocal>?{
        allLiveDataLocalV = vaccionationLocal.findById(id)
        return allLiveDataLocalV
    }

}