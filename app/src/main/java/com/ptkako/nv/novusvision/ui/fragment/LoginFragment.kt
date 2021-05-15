package com.ptkako.nv.novusvision.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.ptkako.nv.novusvision.R
import com.ptkako.nv.novusvision.ui.activity.HomeActivity
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment(R.layout.fragment_login) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnLogin.setOnClickListener {
            activity?.startActivity(Intent(activity, HomeActivity::class.java))
            activity?.finish()
        }

        btnLoginSignup.setOnClickListener{
            it.findNavController().navigate(R.id.action_loginFragment_to_signupFragment)
        }

    }

}