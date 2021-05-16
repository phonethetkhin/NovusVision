package com.ptkako.nv.novusvision.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.ptkako.nv.novusvision.R
import com.ptkako.nv.novusvision.databinding.FragmentSignupBinding
import fragmentViewBinding

class SignupFragment : Fragment(R.layout.fragment_signup) {
    private val binding by fragmentViewBinding(FragmentSignupBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSignupLogin.setOnClickListener {
            it.findNavController().popBackStack()
        }
    }
}