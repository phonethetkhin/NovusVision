package com.ptkako.nv.novusvision.ui.fragment

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.ptkako.nv.novusvision.R
import com.ptkako.nv.novusvision.databinding.FragmentVerifyEmailBinding
import com.ptkako.nv.novusvision.utility.convertMMSSFormat
import com.ptkako.nv.novusvision.utility.kodeinViewModel
import com.ptkako.nv.novusvision.utility.showToast
import com.ptkako.nv.novusvision.viewmodel.AuthViewModel
import fragmentViewBinding
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.x.closestDI

class VerifyEmailFragment : Fragment(R.layout.fragment_verify_email), DIAware {
    override val di: DI by closestDI()

    private val binding by fragmentViewBinding(FragmentVerifyEmailBinding::bind)

    private val viewModel: AuthViewModel by kodeinViewModel()
    private lateinit var timer: CountDownTimer

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        retainInstance = true

        binding.btnResendMail.setText(getString(R.string.resent_mail, "01:00"))

        viewModel.handleBtnResentLiveData.observe(viewLifecycleOwner, Observer {
            binding.btnResendMail.isEnabled = it
        })

        timer = object : CountDownTimer(60 * 1000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                binding.btnResendMail.setText(getString(R.string.resent_mail,
                    convertMMSSFormat(millisUntilFinished / 1000)))
            }

            override fun onFinish() {
                binding.btnResendMail.setText(getString(R.string.resent_mail, "01:00"))
                viewModel.handleBtnResentLiveData.value = true
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

    }

    private fun startTimer() {
        viewModel.handleBtnResentLiveData.value = false
        timer.start()
    }

    override fun onDestroyOptionsMenu() {
        super.onDestroyOptionsMenu()
        timer.cancel()
    }

}