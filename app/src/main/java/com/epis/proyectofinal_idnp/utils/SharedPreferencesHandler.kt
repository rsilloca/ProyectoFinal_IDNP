package com.epis.proyectofinal_idnp.utils

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.epis.proyectofinal_idnp.R
import com.epis.proyectofinal_idnp.models.UserLocation

class SharedPreferencesHandler(context: Context) {

    private var activity: Context = context
    private var preferences: SharedPreferences = activity.getSharedPreferences(
        activity.getString(R.string.preferences_file_key),
        Application.MODE_PRIVATE
    )
    private var editPreferences: SharedPreferences.Editor = preferences.edit()

    // GETTERS
    fun getUserToken(): String? {
        return preferences.getString(activity.getString(R.string.user_token_key), "")
    }

    fun getDepartment(): Int {
        return preferences.getInt(activity.getString(R.string.department_key), 0)
    }

    fun getProvince(): Int {
        return preferences.getInt(activity.getString(R.string.province_key), 0)
    }

    fun getLocation(): UserLocation {
        val label = getLabelLocation()
        val latitude = getLatitude()
        val longitude = getLongitude()
        return UserLocation(label, latitude, longitude)
    }

    private fun getLabelLocation(): String {
        return preferences.getString(activity.getString(R.string.label_location_key), "Location")!!
    }

    private fun getLatitude(): Long {
        return preferences.getLong(activity.getString(R.string.latitude_key), 0L)
    }

    private fun getLongitude(): Long {
        return preferences.getLong(activity.getString(R.string.longitude_key), 0L)
    }

    // SETTERS
    private fun setUserToken(token: String) {
        editPreferences.putString(activity.getString(R.string.user_token_key), token)
        editPreferences.commit()
    }

    private fun setDepartment(departmentId: Int) {
        editPreferences.putInt(activity.getString(R.string.department_key), departmentId)
        editPreferences.commit()
    }

    private fun setProvince(provinceId: Int) {
        editPreferences.putInt(activity.getString(R.string.province_key), provinceId)
        editPreferences.commit()
    }

    fun setLocation(location: UserLocation) {
        setLabelLocation(location.label)
        setLatitude(location.latitude)
        setLongitude(location.longitude)
    }

    private fun setLabelLocation(label: String) {
        editPreferences.putString(activity.getString(R.string.label_location_key), label)
        editPreferences.commit()
    }

    private fun setLatitude(latitude: Long) {
        editPreferences.putLong(activity.getString(R.string.latitude_key), latitude)
        editPreferences.commit()
    }

    private fun setLongitude(longitude: Long) {
        editPreferences.putLong(activity.getString(R.string.longitude_key), longitude)
        editPreferences.commit()
    }

}