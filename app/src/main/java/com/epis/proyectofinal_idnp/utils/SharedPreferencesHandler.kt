package com.epis.proyectofinal_idnp.utils

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.epis.proyectofinal_idnp.R
import com.epis.proyectofinal_idnp.data.model.UserLocation

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

    private fun getLatitude(): Double {
        return preferences.getString(activity.getString(R.string.latitude_key), "0.0")!!.toDouble()
    }

    private fun getLongitude(): Double {
        return preferences.getString(activity.getString(R.string.longitude_key), "0.0")!!.toDouble()
    }

    // SETTERS
    fun setUserToken(token: String) {
        editPreferences.putString(activity.getString(R.string.user_token_key), token)
        editPreferences.commit()
    }

    fun setDepartment(departmentId: Int) {
        editPreferences.putInt(activity.getString(R.string.department_key), departmentId)
        editPreferences.commit()
    }

    fun setProvince(provinceId: Int) {
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

    private fun setLatitude(latitude: Double) {
        editPreferences.putString(activity.getString(R.string.latitude_key), latitude.toString())
        editPreferences.commit()
    }

    private fun setLongitude(longitude: Double) {
        editPreferences.putString(activity.getString(R.string.longitude_key), longitude.toString())
        editPreferences.commit()
    }

}