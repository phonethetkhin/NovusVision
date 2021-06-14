package com.ptkako.nv.novusvision.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.ptkako.nv.novusvision.R
import com.ptkako.nv.novusvision.databinding.DialogUpdateEmailLayoutBinding
import com.ptkako.nv.novusvision.listener.ChangeEmailListener
import com.ptkako.nv.novusvision.utility.isEmailDataInvalid

class ChangeEmailDialog(private val changeEmailListener: ChangeEmailListener) : DialogFragment() {
    private var _binding: DialogUpdateEmailLayoutBinding? = null
    private val binding: DialogUpdateEmailLayoutBinding get() = _binding!!

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        retainInstance = true
        isCancelable = false
        return super.onCreateDialog(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = DialogUpdateEmailLayoutBinding.inflate(inflater, container, false)

        binding.btnSubmit.setOnClickListener {
            val email = binding.tetChangeEmail.text.toString().trim()

            when {
                email.isEmpty() -> {
                    binding.tilChangeEmail.isErrorEnabled = true
                    binding.tilChangeEmail.error = getString(R.string.enter_email)
                }
                isEmailDataInvalid(email) -> {
                    binding.tilChangeEmail.isErrorEnabled = true
                    binding.tilChangeEmail.error = getString(R.string.email_invalid)
                }
                else -> {
                    binding.tilChangeEmail.isErrorEnabled = false
                    dismiss()
                    changeEmailListener.onEmailChange(email)
                }
            }
        }

        binding.btnCancel.setOnClickListener {
            dismiss()
        }


        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}