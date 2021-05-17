package com.ptkako.nv.novusvision.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ptkako.nv.novusvision.R
import com.ptkako.nv.novusvision.databinding.FragmentSignupBinding
import fragmentViewBinding

class SignupFragment : Fragment(R.layout.fragment_signup) {
    private val binding by fragmentViewBinding(FragmentSignupBinding::bind)
    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db = Firebase.firestore
        auth = Firebase.auth
        setBinding()

    }

    private fun setBinding() = with(binding)
    {
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

        //register with auth
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
            }


    }
}