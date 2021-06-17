package com.ptkako.nv.novusvision.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseUser
import com.ptkako.nv.novusvision.R
import com.ptkako.nv.novusvision.databinding.FragmentLoginBinding
import com.ptkako.nv.novusvision.dialog.ProgressDialogFragment
import com.ptkako.nv.novusvision.ui.activity.HomeActivity
import com.ptkako.nv.novusvision.utility.*
import com.ptkako.nv.novusvision.viewmodel.AuthViewModel
import fragmentViewBinding
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.x.closestDI
import org.kodein.di.instance

class LoginFragment : Fragment(R.layout.fragment_login), DIAware {
    override val di: DI by closestDI()

    private val binding by fragmentViewBinding(FragmentLoginBinding::bind)
    private val viewModel: AuthViewModel by kodeinViewModel()
    private val progressDialog: ProgressDialogFragment by instance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        retainInstance = true

        viewModel.userLoginLivedata.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                progressDialog.dismiss()
            }
            when (it) {
                is FirebaseUser -> {
                    if (it.isEmailVerified) {
                        setStringPref(requireContext(), PREF_GMAIL, PREF_GMAIL, it.email!!)
                        setStringPref(requireContext(), PREF_PASSWORD, PREF_PASSWORD, binding.tetLoginPassword.text.toString())
                        requireActivity().startActivity(Intent(activity, HomeActivity::class.java))
                        requireActivity().finish()
                    } else {
                        requireActivity().showToast(getString(R.string.please_verify_your_email))
                        viewModel.userLoginLivedata.value = null
                    }
                }

                is String -> {
                    requireActivity().showToast(it)
                    viewModel.userLoginLivedata.value = null
                }
            }
        })

        binding.btnLogin.setOnClickListener {
            userLogin()
        }

        binding.btnLoginSignup.setOnClickListener {
            it.findNavController().navigate(R.id.action_loginFragment_to_signupFragment)
        }
    }

    private fun userLogin() {
        if (isEmailInValid() or isPasswordInValid()) {
            return
        } else {
            progressDialog.title = getString(R.string.login)
            progressDialog.message = getString(R.string.please_wait)
            progressDialog.show(requireActivity().supportFragmentManager, "Dialog Fragment")
            viewModel.userLogin(binding.tetLoginEmail.text.toString().trim(), binding.tetLoginPassword.text.toString().trim())
        }
    }

    private fun isEmailInValid(): Boolean {
        return when {
            binding.tetLoginEmail.text.toString().trim().isEmpty() -> {
                binding.tilLoginEmail.isErrorEnabled = true
                binding.tilLoginEmail.error = getString(R.string.enter_email)
                true
            }
            isEmailDataInvalid(binding.tetLoginEmail.text.toString().trim()) -> {
                binding.tilLoginEmail.isErrorEnabled = true
                binding.tilLoginEmail.error = getString(R.string.email_invalid)
                true
            }
            else -> {
                binding.tilLoginEmail.isErrorEnabled = false
                false
            }
        }
    }

    private fun isPasswordInValid(): Boolean {
        return when {
            binding.tetLoginPassword.text.toString().trim().isEmpty() -> {
                binding.tilLoginPassword.isErrorEnabled = true
                binding.tilLoginPassword.error = getString(R.string.enter_password)
                true
            }
            else -> {
                binding.tilLoginPassword.isErrorEnabled = false
                false
            }
        }
    }
}