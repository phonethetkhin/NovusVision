package com.ptkako.nv.novusvision.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.*
import androidx.core.view.get
import androidx.fragment.app.DialogFragment
import com.google.android.material.chip.Chip
import com.ptkako.nv.novusvision.databinding.DialogChipLayoutBinding
import com.ptkako.nv.novusvision.viewmodel.EntireListViewModel

class ChipDialog(
    private val viewModel: EntireListViewModel,
    private val setChip:()-> Unit
) : DialogFragment() {
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

        val chipCount = binding.chgEntireList.childCount
        for (c in 0 until chipCount) {
            val chip = binding.chgEntireList.get(c) as Chip
            if (viewModel.chipTextList.contains(chip.text.toString())) {
                chip.isChecked = true
            }
        }

        binding.btnOk.setOnClickListener {
            viewModel.chipTextList.clear()
            for (c in 0 until chipCount) {
                val chip = binding.chgEntireList.get(c) as Chip
                if (chip.isChecked) {
                    viewModel.chipTextList.add(chip.text.toString())
                }
            }
            dismiss()
            setChip.invoke()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}