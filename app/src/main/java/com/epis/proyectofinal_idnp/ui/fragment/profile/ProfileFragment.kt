package com.epis.proyectofinal_idnp.ui.fragment.profile

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.epis.proyectofinal_idnp.databinding.FragmentHomeBinding
import com.epis.proyectofinal_idnp.databinding.FragmentProfileBinding
import com.epis.proyectofinal_idnp.ui.activity.auth.AuthenticationActivity

class ProfileFragment : Fragment() {

    companion object {
        fun newInstance() = ProfileFragment()
    }

    private lateinit var profileViewModel: ProfileViewModel
    private var _binding: FragmentProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        profileViewModel =
                ViewModelProvider(this).get(ProfileViewModel::class.java)

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        /*val textView: EditText = binding.input_name_profile
        profileViewModel.visibility.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        val upgradeBtn = binding.actBtnProfile
        upgradeBtn.setOnClickListener{
            //(activity as AuthenticationActivity).register()
        }

        val toggleVisiblePwd = binding.acBtnVisibility
        toggleVisiblePwd.setOnClickListener {
            //profileViewModel.toggleVisibility()
        }*/

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}