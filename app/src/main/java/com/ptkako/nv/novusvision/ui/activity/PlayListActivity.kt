package com.ptkako.nv.novusvision.ui.activity

import activityViewBinding
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.ptkako.nv.novusvision.adapter.PlayListAdapter
import com.ptkako.nv.novusvision.databinding.ActivityPlayListBinding
import com.ptkako.nv.novusvision.utility.kodeinViewModel
import com.ptkako.nv.novusvision.viewmodel.PlayListViewModel
import org.kodein.di.DIAware
import org.kodein.di.android.closestDI
import org.kodein.di.instance

class PlayListActivity : AppCompatActivity(), DIAware {
    override val di by closestDI()
    private val playListViewModel: PlayListViewModel by kodeinViewModel()
    private val firebaseAuth: FirebaseAuth by instance()
    private lateinit var playListAdapter: PlayListAdapter

    private val binding by activityViewBinding(ActivityPlayListBinding::inflate)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.include4.tlbToolbar)
        supportActionBar!!.title = "My Playlist"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        playListAdapter = PlayListAdapter(this)
        binding.rcvPlaylist.setHasFixedSize(true)
        binding.rcvPlaylist.adapter = playListAdapter
        setVisibility()
        playListViewModel.getPlayListLiveData(firebaseAuth.currentUser!!.uid).observe(this, {
            playListAdapter.submitList(it)
            playListViewModel.pgbPlaylist = View.GONE
            playListViewModel.rcvPlaylist = View.VISIBLE
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
        pgbPlaylist.visibility = playListViewModel.pgbPlaylist
        rcvPlaylist.visibility = playListViewModel.rcvPlaylist
    }
}