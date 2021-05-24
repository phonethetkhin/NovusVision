package com.ptkako.nv.novusvision.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.ptkako.nv.novusvision.R
import com.ptkako.nv.novusvision.databinding.FragmentVerifyEmailBinding
import fragmentViewBinding

class VerifyEmailFragment : Fragment(R.layout.fragment_verify_email) {
    private val binding by fragmentViewBinding(FragmentVerifyEmailBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }
}