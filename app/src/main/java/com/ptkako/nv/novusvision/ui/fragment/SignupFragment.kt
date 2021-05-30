package com.ptkako.nv.novusvision.ui.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.ptkako.nv.novusvision.R
import com.ptkako.nv.novusvision.common.PROGRESS_DIALOG_TAB
import com.ptkako.nv.novusvision.databinding.FragmentSignupBinding
import com.ptkako.nv.novusvision.dialog.ProgressDialogFragment
import com.ptkako.nv.novusvision.utility.isEmailDataInvalid
import com.ptkako.nv.novusvision.utility.isInternetAvailable
import com.ptkako.nv.novusvision.utility.kodeinViewModel
import com.ptkako.nv.novusvision.utility.showToast
import com.ptkako.nv.novusvision.viewmodel.AuthViewModel
import fragmentViewBinding
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.x.closestDI
import org.kodein.di.instance

class SignupFragment : Fragment(R.layout.fragment_signup), DIAware {
    override val di: DI by closestDI()
    private val binding by fragmentViewBinding(FragmentSignupBinding::bind)

    private val viewModel: AuthViewModel by kodeinViewModel()
    private val progressDialog: ProgressDialogFragment by instance()
    private var selectedQuestion = 0
    private lateinit var securityQuestion: Array<String>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        retainInstance = true
        setBinding()

        viewModel.registerUserLiveData.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                progressDialog.dismiss()
            }
            when (it) {

                is String -> {
                    requireActivity().showToast("Sign up successfully.")
                    viewModel.registerUserLiveData.postValue(null)
                    view.findNavController().navigate(R.id.action_signupFragment_to_verifyEmailFragment)
                }

                is Exception -> {
                    requireActivity().showToast(it.localizedMessage)
                    viewModel.registerUserLiveData.postValue(null)
                }
            }
        })
    }

    private fun setBinding() = with(binding) {
        securityQuestion = resources.getStringArray(R.array.security_question)
        tetSignupQuestion.setText(securityQuestion[selectedQuestion])

        btnSignupLogin.setOnClickListener {
            it.findNavController().popBackStack()
        }

        btnSignup.setOnClickListener {
            registerUser()
        }

        tetSignupQuestion.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            builder.setCancelable(false)

            builder.setSingleChoiceItems(securityQuestion, selectedQuestion) { dialog, which ->
                selectedQuestion = which
                tetSignupQuestion.setText(securityQuestion[which])
                dialog.dismiss()
            }

            builder.setPositiveButton(getString(R.string.close)) { dialog, _ ->
                dialog.dismiss()
            }
            builder.create().show()
        }
    }


    private fun registerUser() {
        if (isEmailInValid() or isPasswordInValid()
            or isConfirmPasswordInvalid() or isSignupAnswerInvalid()
        ) {
            return
        } else {
            if (requireActivity().isInternetAvailable()) {

                val email = binding.tetSignupEmail.text.toString().trim()
                val password = binding.tetSignupPassword.text.toString().trim()
                val recoveryQuestion = binding.tetSignupQuestion.text.toString().trim()
                val recoveryAnswer = binding.tetSignupAnswer.text.toString().trim()
                val vipPlan = 0

                val user = hashMapOf(
                    "email" to email,
                    "password" to password,
                    "recovery_question" to recoveryQuestion,
                    "recover_answer" to recoveryAnswer,
                    "vip_plan" to vipPlan.toString()
                )

                progressDialog.title = getString(R.string.sign_up)
                progressDialog.message = getString(R.string.please_wait)
                progressDialog.show(requireActivity().supportFragmentManager, PROGRESS_DIALOG_TAB)

                viewModel.registerUser(user)

            } else {
                requireActivity().showToast(getString(R.string.check_connection))
            }
        }
    }

    private fun isEmailInValid(): Boolean {
        return when {
            binding.tetSignupEmail.text.toString().trim().isEmpty() -> {
                binding.tilSignupEmail.isErrorEnabled = true
                binding.tilSignupEmail.error = getString(R.string.enter_email)
                true
            }
            isEmailDataInvalid(binding.tetSignupEmail.text.toString().trim()) -> {
                binding.tilSignupEmail.isErrorEnabled = true
                binding.tilSignupEmail.error = getString(R.string.email_invalid)
                true
            }
            else -> {
                binding.tilSignupEmail.isErrorEnabled = false
                false
            }
        }
    }

    private fun isPasswordInValid(): Boolean {
        return when {
            binding.tetSignupPassword.text.toString().trim().isEmpty() -> {
                binding.tilSignupPassword.isErrorEnabled = true
                binding.tilSignupPassword.error = getString(R.string.enter_password)
                true
            }
            binding.tetSignupPassword.text.toString().trim().length <= 6 -> {
                binding.tilSignupPassword.isErrorEnabled = true
                binding.tilSignupPassword.error = getString(R.string.short_password)
                true
            }
            else -> {
                binding.tilSignupPassword.isErrorEnabled = false
                false
            }
        }
    }

    private fun isConfirmPasswordInvalid(): Boolean {
        return when {
            binding.tetSignupConfirmPassword.text.toString().trim().isEmpty() -> {
                binding.tilSignupConfirmPassword.isErrorEnabled = true
                binding.tilSignupConfirmPassword.error = getString(R.string.confirm_your_password)
                true
            }
            binding.tetSignupConfirmPassword.text.toString().trim() !=
                    binding.tetSignupPassword.text.toString().trim() -> {
                binding.tilSignupConfirmPassword.isErrorEnabled = true
                binding.tilSignupConfirmPassword.error = getString(R.string.password_not_match)
                true
            }
            else -> {
                binding.tilSignupConfirmPassword.isErrorEnabled = false
                false
            }
        }
    }

    private fun isSignupAnswerInvalid(): Boolean {
        return if (binding.tetSignupAnswer.text.toString().trim().isEmpty()) {
            binding.tilSignupAnswer.isErrorEnabled = true
            binding.tilSignupAnswer.error = getString(R.string.enter_security_answer)
            true
        } else {
            binding.tilSignupAnswer.isErrorEnabled = false
            false
        }
    }
}