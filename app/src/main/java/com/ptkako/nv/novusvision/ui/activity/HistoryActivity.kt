package com.ptkako.nv.novusvision.ui.activity

import activityViewBinding
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.ptkako.nv.novusvision.adapter.HistoryAdapter
import com.ptkako.nv.novusvision.adapter.PlayListAdapter
import com.ptkako.nv.novusvision.databinding.ActivityHistoryBinding
import com.ptkako.nv.novusvision.utility.kodeinViewModel
import com.ptkako.nv.novusvision.viewmodel.HistoryViewModel
import org.kodein.di.DIAware
import org.kodein.di.android.closestDI
import org.kodein.di.instance

class HistoryActivity : AppCompatActivity(), DIAware {
    override val di by closestDI()
    private val historyViewModel: HistoryViewModel by kodeinViewModel()
    private val firebaseAuth: FirebaseAuth by instance()
    private lateinit var historyAdapter: HistoryAdapter
    private val binding by activityViewBinding(ActivityHistoryBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.include5.tlbToolbar)
        supportActionBar!!.title = "My Watch History"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        historyAdapter = HistoryAdapter(this)
        binding.rcvHistory.setHasFixedSize(true)
        binding.rcvHistory.adapter = historyAdapter
        setVisibility()
        historyViewModel.getHistoryListLiveData(firebaseAuth.currentUser!!.uid).observe(this, {
            historyAdapter.submitList(it)
            historyViewModel.pgbHistory = View.GONE
            historyViewModel.rcvHistory = View.VISIBLE
            setVisibility()
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> super.onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setVisibility() = with(binding) {
        pgbHistory.visibility = historyViewModel.pgbHistory
        rcvHistory.visibility = historyViewModel.rcvHistory
    }
}