package com.epis.proyectofinal_idnp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.epis.proyectofinal_idnp.data.converter.Converters
import com.epis.proyectofinal_idnp.data.dao.*
import com.epis.proyectofinal_idnp.data.model.*

@Database(
    entities = [User::class, Department::class, Province::class, VaccinationEvent::class, VaccinationSite::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class DatabaseConfig : RoomDatabase() {
    // Declaration DAOs
    abstract fun userDao(): UserDao
    abstract fun vaccinationEventDao(): VaccinationEventDao

    // Database Invocation
    companion object {
        @Volatile
        private var INSTANCE: DatabaseConfig? = null
        fun getDatabase(context: Context): DatabaseConfig {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DatabaseConfig::class.java,
                    "word_database"
                ).allowMainThreadQueries().build()
                INSTANCE = instance
                instance
            }
        }
    }
}