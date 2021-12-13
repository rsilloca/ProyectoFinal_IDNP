package com.epis.proyectofinal_idnp.ui.fragment.profile

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.epis.proyectofinal_idnp.databinding.FragmentProfileBinding
import com.epis.proyectofinal_idnp.firebase.model.User

class ProfileFragment : Fragment() {

    companion object {
        fun newInstance() = ProfileFragment()
    }

    private lateinit var profileViewModel: ProfileViewModel
    private var _binding: FragmentProfileBinding? = null
    private lateinit var  currentUser : User

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

        showUserData()
        /*val textView: EditText = binding.input_name_profile
        profileViewModel.visibility.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })*/

        val upgradeBtn = binding.actBtnProfile
        upgradeBtn.setOnClickListener{
            updateDataUser()
            Toast.makeText(context, "User Data Updated", Toast.LENGTH_SHORT).show()
        }

        val upgradeBtnPass = binding.acBtnNewPassword
        upgradeBtnPass.setOnClickListener{
           updatePasswordUser()
        }

        val toggleVisiblePwd = binding.acBtnVisibility
        toggleVisiblePwd.setOnClickListener {
            //profileViewModel.toggleVisibility()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showUserData() {
        profileViewModel.getCurrentUserData()?.observe(viewLifecycleOwner, Observer {
            binding.inputNameProfile.text = Editable.Factory.getInstance()
                .newEditable(it.fullname)
            binding.inputPhoneProfile.text = Editable.Factory.getInstance()
                .newEditable(it.phoneNumber.toString())
            binding.inputEmailProfile.text = Editable.Factory.getInstance()
                .newEditable(profileViewModel.getUserFbEmail())
            currentUser = it
        })
    }

    private fun updateDataUser() {
        val cUser = User(
            binding.inputNameProfile.text.toString(),
            binding.inputPhoneProfile.text.toString().toInt(),
            currentUser.vaccinatedDate,
            currentUser.vaccineType,
        )
        cUser.documentId = currentUser.documentId
        profileViewModel.updateCurrentUser(cUser, binding.inputEmailProfile.text.toString())
    }

    private fun updatePasswordUser() {
        val email = profileViewModel.getUserFbEmail().toString()
        val oldPass = binding.inputPwdProfile.text.toString()
        val newPass = binding.inputNewPwdProfile.text.toString()

        binding.inputPwdProfile.text = null
        binding.inputNewPwdProfile.text = null

        profileViewModel.updatePassword(email, oldPass, newPass, context)
    }
}