package com.ptkako.nv.novusvision.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ptkako.nv.novusvision.R
import com.ptkako.nv.novusvision.databinding.FragmentLoginBinding
import com.ptkako.nv.novusvision.ui.activity.HomeActivity
import fragmentViewBinding

class LoginFragment : Fragment(R.layout.fragment_login) {
    private val binding by fragmentViewBinding(FragmentLoginBinding::bind)
    private lateinit var auth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth
        binding.btnLogin.setOnClickListener {
            //sample data
            val email = "snowcat018@gmail.com"
            val password = "abcdefg"
            userLogin(email, password)
        }

        binding.btnLoginSignup.setOnClickListener {
            it.findNavController().navigate(R.id.action_loginFragment_to_signupFragment)
        }
    }

    private fun userLogin(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser
                    Log.d("Login", "signInWithEmail:success")
                    activity?.startActivity(Intent(activity, HomeActivity::class.java))
                    activity?.finish()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.d("Login", "signInWithEmail:failure", task.exception)
                }
            }
    }
}