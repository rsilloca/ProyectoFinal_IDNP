package com.epis.proyectofinal_idnp.firebase.repository

import com.epis.proyectofinal_idnp.firebase.livedata.DocumentReferenceFirebaseLiveData
import com.epis.proyectofinal_idnp.firebase.livedata.MultipleDocumentReferenceLiveData
import com.epis.proyectofinal_idnp.firebase.model.FirebaseEntity
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.Query


interface CrudFirebaseRepository<E : FirebaseEntity, I> {
    /**
     * Son todos los datos de la entidad.
     *
     * @return LiveData de los documentos referenciados.
     */
    fun findAll(): MultipleDocumentReferenceLiveData<E, out Query>

    /**
     * Obtener la entidad de firebase.
     *
     * @param identifier Es el identificador.
     * @return LiveData del documento referenciado.
     */
    fun findById(identifier: I): DocumentReferenceFirebaseLiveData<E>

    /**
     * Guardar datos en la Firebase.
     *
     * @param entity Es la entidad a ser persistida en Firebase.
     */
    fun save(entity: E)

    /**
     * Guardar datos en la Firebase.
     *
     * @param entities Es la entidad a ser persistida en Firebase.
     */
    fun saveAll(entities: List<E>)

    /**
     * Actualiza los datos de Firebase.
     *
     * @param entity Es la actualización de la entidad.
     */
    fun update(entity: E)

    /**
     * Actualizar datos en la Firebase.
     *
     * @param entities Es la entidad a ser actualizada en Firebase.
     */
    @Throws(IllegalAccessException::class)
    fun updateAll(entities: List<E>)

    /**
     * Borrar los datos de Firebase.
     *
     * @param entity Es la entidad a borrar.
     */
    fun delete(entity: E): Task<Void>

    /**
     * Borrar datos en la Firebase.
     *
     * @param entities Es la entidad a ser borrada en Firebase.
     */
    fun deleteAll(entities: List<E>)
}
