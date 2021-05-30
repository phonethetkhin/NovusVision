package com.ptkako.nv.novusvision.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.ptkako.nv.novusvision.databinding.ProgressDialogLayoutBinding

class ProgressDialogFragment : DialogFragment() {
    private lateinit var binding: ProgressDialogLayoutBinding
    var title: String? = null
    var message = ""

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
        binding = ProgressDialogLayoutBinding.inflate(inflater, container, false)

        if (title.isNullOrBlank()) {
            binding.txtProgressBarTitle.visibility = View.GONE
        } else {
            binding.txtProgressBarTitle.visibility = View.VISIBLE
            binding.txtProgressBarTitle.text = title
        }

        binding.txtProgressBarMessage.text = message

        return binding.root
    }

}