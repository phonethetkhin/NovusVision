package com.ptkako.nv.novusvision.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseUser
import com.ptkako.nv.novusvision.R
import com.ptkako.nv.novusvision.databinding.FragmentSignupBinding
import com.ptkako.nv.novusvision.dialog.ProgressDialogFragment
import com.ptkako.nv.novusvision.utility.kodeinViewModel
import com.ptkako.nv.novusvision.viewmodel.AuthViewModel
import fragmentViewBinding
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.x.di

class SignupFragment : Fragment(R.layout.fragment_signup), DIAware {
    override val di: DI by di()
    private val binding by fragmentViewBinding(FragmentSignupBinding::bind)
    private val viewModel: AuthViewModel by kodeinViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setBinding()

    }

    private fun setBinding() = with(binding) {
        btnSignupLogin.setOnClickListener {
            it.findNavController().popBackStack()

        }

        btnSignup.setOnClickListener {
            registerUser()
        }
    }


    private fun registerUser() {
        //sample data
        val email = "snowcat018@gmail.com"
        val password = "abcdefg"
        val recoveryQuestion = "What is your school name?"
        val recoveryAnswer = "Ahlone 4"
        val vipPlan = 0

        val progressDialog = ProgressDialogFragment("Loading...", "Please wait...")
        progressDialog.show(requireActivity().supportFragmentManager, "Sdf")

        viewModel.registerUser(email, password)
            .observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                progressDialog.dismiss()
                when (it) {

                    is FirebaseUser -> {
                        println("print result : ${it.uid}")
                    }

                    is Exception -> {
                        println("print result : ${it.localizedMessage}")
                    }
                }
            })

        /* //register with auth
         auth.createUserWithEmailAndPassword(email, password)
             .addOnCompleteListener(requireActivity()) { task ->
                 if (task.isSuccessful) {
                     // Create a new user with a first and last name
                     val user = hashMapOf(
                         "user_id" to auth.currentUser!!.uid,
                         "email" to email,
                         "password" to password,
                         "recovery_question" to recoveryQuestion,
                         "recover_answer" to recoveryAnswer,
                         "vip_plan" to vipPlan
                     )
                     //register with FireStore
                     // Add a new document with a generated ID
                     db.collection("User")
                         .add(user)
                         .addOnSuccessListener { documentReference ->
                             Log.d("Register", "DocumentSnapshot added with ID: ${documentReference.id}")
                         }
                         .addOnFailureListener { e ->
                             Log.d("Register", "Error adding document", e)
                         }
                 } else {
                     // If sign in fails, display a message to the user.
                     Log.d("Register", "createUserWithEmail:failure", task.exception)
                 }
             }*/


    }
}