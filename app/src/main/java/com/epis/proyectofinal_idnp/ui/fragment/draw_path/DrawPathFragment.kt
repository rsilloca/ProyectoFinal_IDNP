package com.epis.proyectofinal_idnp.ui.fragment.draw_path

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.epis.proyectofinal_idnp.R
import android.util.Log

class DrawPathFragment : Fragment() {

    companion object {
        fun newInstance() = DrawPathFragment()
    }

    private lateinit var viewModel: DrawPathViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_draw_path, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(DrawPathViewModel::class.java)
        viewModel.latitude.observe(viewLifecycleOwner, {
            Log.d("Latitude", it.toString())
            Log.d("Longitude", viewModel.longitude.toString())
        })
    }

}