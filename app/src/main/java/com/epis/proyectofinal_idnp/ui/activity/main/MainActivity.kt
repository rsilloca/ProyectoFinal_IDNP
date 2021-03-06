package com.epis.proyectofinal_idnp.ui.activity.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Toast
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.epis.proyectofinal_idnp.R
import com.epis.proyectofinal_idnp.data.model.UserLocation
import com.epis.proyectofinal_idnp.data.model.VaccinationLocation
import com.epis.proyectofinal_idnp.databinding.ActivityMainBinding
import com.epis.proyectofinal_idnp.firebase.service.AuthService
import com.epis.proyectofinal_idnp.ui.activity.auth.AuthenticationActivity
import com.epis.proyectofinal_idnp.ui.fragment.draw_path.DrawPathFragment
import com.epis.proyectofinal_idnp.ui.fragment.home.HomeFragment
import com.epis.proyectofinal_idnp.ui.fragment.select_department.SelectDepartmentFragment
import com.epis.proyectofinal_idnp.ui.fragment.select_location.SelectLocationFragment
import com.epis.proyectofinal_idnp.ui.fragment.vaccination_locations.VaccinationLocationsFragment
import com.epis.proyectofinal_idnp.utils.SharedPreferencesHandler
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var preferences: SharedPreferencesHandler
    private var idDepartment by Delegates.notNull<Int>()
    private var idProvince by Delegates.notNull<Int>()
    private lateinit var location: UserLocation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        preferences = SharedPreferencesHandler(this)

        idDepartment = preferences.getDepartment()
        idProvince = preferences.getProvince()
        location = preferences.getLocation()

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val menuConfig: Set<Int> = setOf(
            R.id.nav_home,
            R.id.nav_vaccination_locations,
            R.id.nav_reminder,
            R.id.nav_statistics,
            R.id.nav_favorites,
            R.id.nav_profile,
            R.id.nav_logout
        )
        appBarConfiguration = AppBarConfiguration(menuConfig, drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        navView.menu[menuConfig.size - 1].setOnMenuItemClickListener {
            logout()
            true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun logout() {
        preferences.setUserToken("")
        AuthService.firebaseSingOut()
        Log.e("TAG", "Singout succesfull")
        Toast.makeText(baseContext, "Singout succesful", Toast.LENGTH_SHORT).show()
        val authIntent = Intent(this, AuthenticationActivity::class.java)
        startActivity(authIntent)
    }

    fun goSelectDepartment() {
        if (thereIsLocationDefault()) {
            gotoVaccinationLocations("",0.0,0.0)
        } else {
            supportFragmentManager.commit {
                replace<SelectDepartmentFragment>(R.id.nav_host_fragment_content_main)
                // setReorderingAllowed(true)
                // addToBackStack("SelectDepartment")
            }
        }
    }

    fun goSelectLocation(_idDepartment: Int, _idProvince: Int) {
        idDepartment = _idDepartment
        idProvince = _idProvince
        preferences.setDepartment(idDepartment)
        preferences.setProvince(idProvince)
        supportFragmentManager.commit {
            replace<SelectLocationFragment>(R.id.nav_host_fragment_content_main)
            // setReorderingAllowed(true)
            // addToBackStack("SelectLocation")
        }
    }

    fun gotoVaccinationLocations(_label: String, _latitude: Double, _longitude: Double) {
        if (_label != "" && _latitude != 0.0 && _longitude != 0.0) {
            location = UserLocation(_label, _latitude, _longitude)
            preferences.setLocation(location)
        }
        val bundle = Bundle()
        bundle.putInt("idDepartment", preferences.getDepartment())
        bundle.putInt("idProvince", preferences.getProvince())
        val fragment = VaccinationLocationsFragment()
        fragment.arguments = bundle
        supportFragmentManager.commit {
            replace(R.id.nav_host_fragment_content_main, fragment)
            // setReorderingAllowed(true)
            // addToBackStack("SelectLocation")
        }
    }

    private fun thereIsLocationDefault(): Boolean {
        return idDepartment != null && idProvince != null && location.latitude != 0.0 && location.longitude != 0.0
    }

    fun gotoHome() {
        supportFragmentManager.commit {
            replace<HomeFragment>(R.id.nav_host_fragment_content_main)
            // setReorderingAllowed(true)
            // addToBackStack("SelectLocation")
        }
    }

    fun viewRoute(location: VaccinationLocation) {
        val bundle = Bundle()
        bundle.putDouble("latitudeFrom", preferences.getLocation().latitude)
        bundle.putDouble("longitudeFrom", preferences.getLocation().longitude)
        bundle.putSerializable("location", location)
        val fragment = DrawPathFragment()
        fragment.arguments = bundle
        supportFragmentManager.commit {
            replace(R.id.nav_host_fragment_content_main, fragment)
            // setReorderingAllowed(true)
            // addToBackStack("SelectLocation")
        }
    }

}