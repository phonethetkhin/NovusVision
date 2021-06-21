package com.ptkako.nv.novusvision.ui.activity

import activityViewBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.chip.Chip
import com.ptkako.nv.novusvision.R
import com.ptkako.nv.novusvision.adapter.MoviesAdapter
import com.ptkako.nv.novusvision.databinding.ActivityEntireListBinding
import com.ptkako.nv.novusvision.dialog.ChipDialog
import com.ptkako.nv.novusvision.model.MovieModel
import com.ptkako.nv.novusvision.utility.kodeinViewModel
import com.ptkako.nv.novusvision.viewmodel.EntireListViewModel
import org.kodein.di.DIAware
import org.kodein.di.android.closestDI

class EntireListActivity : AppCompatActivity(), DIAware {
    override val di by closestDI()
    private val binding by activityViewBinding(ActivityEntireListBinding::inflate)
    private lateinit var movieAdapter: MoviesAdapter
    private val viewModel: EntireListViewModel by kodeinViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        //movieAdapter = MoviesAdapter(this)
        setSupportActionBar(binding.include3.tlbToolbar)
        supportActionBar!!.title = "Recommended"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        movieAdapter = MoviesAdapter(this)

        val statusCode: String? = intent.getStringExtra("statusCode")
        binding.pgbEntire.visibility = viewModel.pgbEntire
        viewModel.getMoviesListLiveData(statusCode).observe(this) {
            println("observe data")
            movieAdapter.submitList(it)
            viewModel.pgbEntire = View.GONE
            binding.pgbEntire.visibility = viewModel.pgbEntire
        }
        setBinding()
    }

    private fun setBinding() = with(binding)
    {
        rcvEntireList.setHasFixedSize(true)
        rcvEntireList.adapter = movieAdapter
    }

    private fun setChip() {
        binding.chgEntireList.removeAllViews()
        val chipTextList = viewModel.chipTextList
        if (chipTextList.isNotEmpty()) {
            val moviesList = viewModel.moviesListLiveData.value
            val filterMovies = moviesList!!.filter { it.genres.split(",").filter { viewModel.chipTextList.contains(it) }.isNotEmpty() }
            println("observe data ${filterMovies.size}")
            viewModel.moviesListLiveData.value = filterMovies as ArrayList<MovieModel>
        }
        val inflater = LayoutInflater.from(this)
        for (text in chipTextList) {
            val chipItem = inflater.inflate(R.layout.chip_item, null, false) as Chip
            chipItem.text = text
            binding.chgEntireList.addView(chipItem)
            chipItem.setOnCloseIconClickListener {
                viewModel.chipTextList.remove(text)
                binding.chgEntireList.removeView(chipItem)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.entire_list_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> super.onBackPressed()

            R.id.menu_filter -> ChipDialog(viewModel) { setChip() }
                .show(supportFragmentManager, "Chip Dialog")
        }
        return super.onOptionsItemSelected(item)
    }

}