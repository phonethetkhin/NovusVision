package com.ptkako.nv.novusvision.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.*
import androidx.core.view.allViews
import androidx.fragment.app.DialogFragment
import com.ptkako.nv.novusvision.databinding.DialogChipLayoutBinding

class ChipDialog : DialogFragment() {
    private var _binding: DialogChipLayoutBinding? = null
    private val binding: DialogChipLayoutBinding get() = _binding!!

    override fun onStart() {
        super.onStart()
        val window = dialog?.window
        window?.apply {
            val attr = attributes
            setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
            attr.gravity = Gravity.TOP
            attributes = attr
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        retainInstance = true
        isCancelable = false
        return super.onCreateDialog(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = DialogChipLayoutBinding.inflate(inflater, container, false)
        binding.btnOk.setOnClickListener {
            dismiss()
        }

        val chip = binding.chgEntireList.allViews

        chip.forEach {
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}