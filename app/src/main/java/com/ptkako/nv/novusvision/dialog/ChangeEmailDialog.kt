package com.ptkako.nv.novusvision.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.ptkako.nv.novusvision.R
import com.ptkako.nv.novusvision.databinding.DialogUpdateEmailLayoutBinding
import com.ptkako.nv.novusvision.utility.isEmailDataInvalid
import com.ptkako.nv.novusvision.utility.showToast

class ChangeEmailDialog(val updateEmail: () -> LiveData<Any>) : DialogFragment() {
    private lateinit var binding: DialogUpdateEmailLayoutBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        retainInstance = true
        return super.onCreateDialog(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DialogUpdateEmailLayoutBinding.inflate(inflater, container, false)

        binding.btnSubmit.setOnClickListener {
            binding.lnlProgressDialog.visibility = View.VISIBLE
            val email = binding.tetChangeEmail.text.toString().trim()

            when {
                email.isNotEmpty() -> {
                    binding.tilChangeEmail.isErrorEnabled = false
                    updateEmail().observe(viewLifecycleOwner, Observer {
                        binding.lnlProgressDialog.visibility = View.GONE
                        context?.showToast("Success update")
                        dismiss()
                    })
                }
                isEmailDataInvalid(email) -> {
                    binding.tilChangeEmail.isErrorEnabled = true
                    binding.tilChangeEmail.error = getString(R.string.email_invalid)
                }
                else -> {
                    binding.tilChangeEmail.isErrorEnabled = true
                    binding.tilChangeEmail.error = getString(R.string.enter_email)
                }
            }
        }

        binding.btnCancel.setOnClickListener {
            dismiss()
        }


        return binding.root
    }
}