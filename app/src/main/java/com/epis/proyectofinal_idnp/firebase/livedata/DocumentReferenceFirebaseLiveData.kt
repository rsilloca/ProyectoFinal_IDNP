package com.epis.proyectofinal_idnp.firebase.livedata
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.epis.proyectofinal_idnp.firebase.model.FirebaseEntity
import com.google.firebase.firestore.*

class DocumentReferenceFirebaseLiveData<T : FirebaseEntity?>(// Firebase Utils.
    private val documentReference: DocumentReference, protected val entityClass: Class<T>
) :
    MutableLiveData<T>(), EventListener<DocumentSnapshot?> {
    protected var listenerRegistration = ListenerRegistration {}

    // Entity Utils
    protected var entity: T? = null
    override fun onActive() {
        listenerRegistration = documentReference.addSnapshotListener(this)
        super.onActive()
    }

    override fun onInactive() {
        listenerRegistration.remove()
        super.onInactive()
    }

    override fun onEvent(documentSnapshot: DocumentSnapshot?, error: FirebaseFirestoreException?) {
        if (documentSnapshot != null && !documentSnapshot.exists()) {
            Log.e(TAG, "Updating")
            entity = null
            entity = documentSnapshot.toObject(entityClass)
            entity!!.documentId = documentSnapshot.id
            this.setValue(entity)
        } else if (error != null) {
            Log.e(TAG, error.message, error.cause)
        }
    }

    companion object {
        protected var TAG = DocumentReferenceFirebaseLiveData::class.java.simpleName
    }
}
