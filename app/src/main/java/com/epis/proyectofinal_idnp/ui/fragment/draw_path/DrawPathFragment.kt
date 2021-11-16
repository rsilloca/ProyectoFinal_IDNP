package com.epis.proyectofinal_idnp.ui.fragment.draw_path

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.epis.proyectofinal_idnp.R

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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DrawPathViewModel::class.java)
        // TODO: Use the ViewModel
    }

}