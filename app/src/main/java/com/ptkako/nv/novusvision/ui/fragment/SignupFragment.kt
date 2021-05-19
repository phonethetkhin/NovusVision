package com.ptkako.nv.novusvision.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseUser
import com.ptkako.nv.novusvision.R
import com.ptkako.nv.novusvision.common.PROGRESS_DIALOG_TAB
import com.ptkako.nv.novusvision.databinding.FragmentSignupBinding
import com.ptkako.nv.novusvision.dialog.ProgressDialogFragment
import com.ptkako.nv.novusvision.utility.kodeinViewModel
import com.ptkako.nv.novusvision.utility.showToast
import com.ptkako.nv.novusvision.viewmodel.AuthViewModel
import fragmentViewBinding
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.x.di

class SignupFragment : Fragment(R.layout.fragment_signup), DIAware {
    override val di: DI by di()
    private val binding by fragmentViewBinding(FragmentSignupBinding::bind)
    private val viewModel: AuthViewModel by kodeinViewModel()
    private lateinit var progressDialog: ProgressDialogFragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        retainInstance = true
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
        val email = binding.tetSignupEmail.text.toString().trim()
        val password = binding.tetSignupPassword.text.toString().trim()
        val confirmPassword = binding.tetSignupConfirmPassword.text.toString().trim()
        val recoveryQuestion = binding.tetSignupQuestion.text.toString().trim()
        val recoveryAnswer = binding.tetSignupAnswer.text.toString().trim()
        val vipPlan = 0

        if (isEmailInValid(email) or isPasswordInValid(password)) {
            return
        } else {
            progressDialog = ProgressDialogFragment(getString(R.string.sign_up), getString(R.string.please_wait))
            progressDialog.show(requireActivity().supportFragmentManager, PROGRESS_DIALOG_TAB)

            viewModel.registerUser(email, password).observe(viewLifecycleOwner, Observer {
                progressDialog.dismiss()
                when (it) {

                    is FirebaseUser -> {
                        requireActivity().showToast(it.uid)
                    }

                    is Exception -> {
                        requireActivity().showToast(it.localizedMessage)
                    }
                }
            })
        }

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


    private fun isEmailInValid(email: String?): Boolean {
        return if (email.isNullOrEmpty()) {
            binding.tilSignupEmail.isErrorEnabled = true
            binding.tilSignupEmail.error = "Please Enter Email."
            true
        } else {
            binding.tilSignupEmail.isErrorEnabled = false
            false
        }
    }

    private fun isPasswordInValid(password: String?): Boolean {
        return if (password.isNullOrEmpty()) {
            binding.tilSignupPassword.isErrorEnabled = true
            binding.tilSignupPassword.error = "Please Enter Password."
            true
        } else {
            binding.tilSignupPassword.isErrorEnabled = false
            false
        }
    }
}