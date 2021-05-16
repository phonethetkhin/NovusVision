package com.ptkako.nv.novusvision.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.ptkako.nv.novusvision.R
import com.ptkako.nv.novusvision.databinding.FragmentLoginBinding
import com.ptkako.nv.novusvision.ui.activity.HomeActivity
import fragmentViewBinding

class LoginFragment : Fragment(R.layout.fragment_login) {
    private val binding by fragmentViewBinding(FragmentLoginBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogin.setOnClickListener {
            activity?.startActivity(Intent(activity, HomeActivity::class.java))
            activity?.finish()
        }

        binding.btnLoginSignup.setOnClickListener {
            it.findNavController().navigate(R.id.action_loginFragment_to_signupFragment)
        }
    }

}