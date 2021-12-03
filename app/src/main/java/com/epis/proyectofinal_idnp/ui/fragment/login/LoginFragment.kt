package com.epis.proyectofinal_idnp.ui.fragment.login

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import com.epis.proyectofinal_idnp.databinding.FragmentLoginBinding
import com.epis.proyectofinal_idnp.ui.activity.auth.AuthenticationActivity

class LoginFragment : Fragment() {

    companion object {
        fun newInstance() = LoginFragment()
    }

    private lateinit var loginViewModel: LoginViewModel
    private var _binding: FragmentLoginBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val loginBtn = binding.logBtnLogin
        loginBtn.setOnClickListener {

            val email = binding.inputEmailLogin.text.toString()
            val password = binding.inputPwdLogin.text.toString()
            when {
                email.isEmpty() || password.isEmpty() ->{
                    Toast.makeText(context, "EMPTY FIELDS", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    (activity as AuthenticationActivity).login(email, password)
                }
            }
        }

        val registerBtn = binding.logBtnRegister
        registerBtn.setOnClickListener {
            (activity as AuthenticationActivity).loadRegister()
        }

        val freeAccessBtn = binding.logBtnFreeAccess
        freeAccessBtn.setOnClickListener {
            (activity as AuthenticationActivity).loginAnonymus()
        }

        val toggleVisiblePwd = binding.logBtnVisibility
        toggleVisiblePwd.setOnClickListener {
            loginViewModel.toggleVisibility()
        }

        val inputPwd: EditText = binding.inputPwdLogin
        loginViewModel.visibility.observe(viewLifecycleOwner, Observer {
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