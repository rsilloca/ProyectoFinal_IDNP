package com.epis.proyectofinal_idnp.ui.fragment.register

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.Observer
import com.epis.proyectofinal_idnp.databinding.FragmentRegisterBinding
import com.epis.proyectofinal_idnp.ui.activity.auth.AuthenticationActivity

class RegisterFragment : Fragment() {

    companion object {
        fun newInstance() = RegisterFragment()
    }

    private lateinit var registerViewModel: RegisterViewModel
    private var _binding: FragmentRegisterBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        registerViewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)

        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val registerBtn = binding.regBtnRegister
        registerBtn.setOnClickListener {
            (activity as AuthenticationActivity).register()
        }

        val loginBtn = binding.regBtnLogin
        loginBtn.setOnClickListener {
            (activity as AuthenticationActivity).loadLogin()
        }

        val freeAccessBtn = binding.regBtnFreeAccess
        freeAccessBtn.setOnClickListener {
            (activity as AuthenticationActivity).login()
        }

        val toggleVisiblePwd = binding.regBtnVisibility
        toggleVisiblePwd.setOnClickListener {
            registerViewModel.toggleVisibility()
        }

        val inputPwd: EditText = binding.inputPwdRegister
        registerViewModel.visibility.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                inputPwd.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            } else {
                inputPwd.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
            }
            inputPwd.placeCursorAtLast()
        })

        return root
    }

    fun EditText.placeCursorAtLast() {
        val string = this.text.toString()
        this.setSelection(string.length)
    }

}