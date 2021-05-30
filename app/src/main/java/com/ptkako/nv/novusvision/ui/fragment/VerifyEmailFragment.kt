package com.ptkako.nv.novusvision.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.firebase.auth.FirebaseUser
import com.ptkako.nv.novusvision.R
import com.ptkako.nv.novusvision.databinding.FragmentVerifyEmailBinding
import com.ptkako.nv.novusvision.dialog.ChangeEmailDialog
import com.ptkako.nv.novusvision.dialog.ProgressDialogFragment
import com.ptkako.nv.novusvision.listener.ChangeEmailListener
import com.ptkako.nv.novusvision.ui.activity.HomeActivity
import com.ptkako.nv.novusvision.utility.convertMMSSFormat
import com.ptkako.nv.novusvision.utility.kodeinViewModel
import com.ptkako.nv.novusvision.utility.showToast
import com.ptkako.nv.novusvision.viewmodel.AuthViewModel
import fragmentViewBinding
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.x.closestDI
import org.kodein.di.instance

class VerifyEmailFragment : Fragment(R.layout.fragment_verify_email), DIAware {
    override val di: DI by closestDI()
    private val progressDialogFragment: ProgressDialogFragment by instance()

    private val binding by fragmentViewBinding(FragmentVerifyEmailBinding::bind)

    private val viewModel: AuthViewModel by kodeinViewModel()
    private lateinit var timer: CountDownTimer

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        retainInstance = true

        viewModel.handleBtnTextLiveData.observe(viewLifecycleOwner, Observer {
            binding.btnResendMail.setText(it)
        })

        viewModel.handleBtnResentLiveData.observe(viewLifecycleOwner, Observer {
            binding.btnResendMail.isEnabled = it
        })

        viewModel.updateEmailLiveData.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                requireActivity().showToast(it)
                progressDialogFragment.dismiss()
                viewModel.updateEmailLiveData.value = null
            }
        })

        viewModel.reloadCurrentUserLivedata.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                progressDialogFragment.dismiss()
            }
            when (it) {
                is FirebaseUser -> {
                    if (it.isEmailVerified) {
                        requireActivity().startActivity(Intent(activity, HomeActivity::class.java))
                        requireActivity().finish()
                    } else {
                        requireActivity().showToast(getString(R.string.please_verify_your_email))
                        viewModel.reloadCurrentUserLivedata.value = null
                    }
                }

                is String -> {
                    requireActivity().showToast(it)
                    viewModel.reloadCurrentUserLivedata.value = null
                }
            }
        })

        timer = object : CountDownTimer(60 * 1000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                context?.let {
                    viewModel.handleBtnTextLiveData.value = getString(R.string.resent_mail,
                        "(${convertMMSSFormat(millisUntilFinished / 1000)})")
                }
            }

            override fun onFinish() {
                context?.let {
                    viewModel.handleBtnTextLiveData.value = getString(R.string.resent_mail, "")
                    viewModel.handleBtnResentLiveData.value = true
                }
            }
        }

        if (savedInstanceState == null) {
            startTimer()
        }

        viewModel.registerUserLiveData.observe(viewLifecycleOwner, Observer {

            when (it) {
                is String -> {
                    requireActivity().showToast(it.toString())
                    viewModel.registerUserLiveData.value = null
                }

                is Exception -> {
                    requireActivity().showToast(it.localizedMessage)
                    viewModel.registerUserLiveData.value = null
                }
            }

        })

        binding.btnResendMail.setOnClickListener {
            startTimer()
            viewModel.resendEmailVerification()
        }

        binding.btnAlreadyConfirm.setOnClickListener {
            progressDialogFragment.title = "Checking"
            progressDialogFragment.message = getString(R.string.please_wait)
            progressDialogFragment.show(requireActivity().supportFragmentManager, "Progress Dialog")
            viewModel.reloadCurrentUser()
        }

        binding.btnChangeEmail.setOnClickListener {
            changeEmail()
        }
    }

    private fun changeEmail() {
        val changeEmailDialog = ChangeEmailDialog(object : ChangeEmailListener {
            override fun onEmailChange(email: String) {
                progressDialogFragment.title = "Updating"
                progressDialogFragment.message = getString(R.string.please_wait)
                progressDialogFragment.show(requireActivity().supportFragmentManager, "Progress Dialog")
                viewModel.updateEmail(email)
            }
        })

        changeEmailDialog.show(requireActivity().supportFragmentManager, "Change Email Dialog")
    }

    private fun startTimer() {
        viewModel.handleBtnResentLiveData.value = false
        timer.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
    }

}