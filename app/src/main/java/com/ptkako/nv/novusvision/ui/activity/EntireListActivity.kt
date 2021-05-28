package com.ptkako.nv.novusvision.ui.activity

import activityViewBinding
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.ptkako.nv.novusvision.R
import com.ptkako.nv.novusvision.adapter.movies.MoviesAllAdapter
import com.ptkako.nv.novusvision.databinding.ActivityEntireListBinding

class EntireListActivity : AppCompatActivity() {
    private val binding by activityViewBinding(ActivityEntireListBinding::inflate)
    private lateinit var movieAllAdapter: MoviesAllAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        //movieAdapter = MoviesAdapter(this)
        setSupportActionBar(binding.include3.tlbToolbar)
        supportActionBar!!.title = "Recommended"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

       /* val moviesList = ArrayList<MoviesModel>()
        moviesList.add(MoviesModel(1, R.drawable.captain_marvel, "Captain Marvel"))
        moviesList.add(MoviesModel(2, R.drawable.game_of_throne, "Captain Marvel"))
        moviesList.add(MoviesModel(3, R.drawable.spiderman, "Captain Marvel"))
        moviesList.add(MoviesModel(4, R.drawable.superman, "Captain Marvel"))
        moviesList.add(MoviesModel(5, R.drawable.warcraft, "Captain Marvel"))

        moviesList.add(MoviesModel(6, R.drawable.captain_marvel, "Captain Marvel"))
        moviesList.add(MoviesModel(7, R.drawable.game_of_throne, "Captain Marvel"))
        moviesList.add(MoviesModel(8, R.drawable.spiderman, "Captain Marvel"))
        moviesList.add(MoviesModel(9, R.drawable.superman, "Captain Marvel"))
        moviesList.add(MoviesModel(10, R.drawable.warcraft, "Captain Marvel"))

        movieAdapter.submitList(moviesList)*/
        setBinding()
    }

    private fun setBinding() = with(binding)
    {
        rcvEntireList.setHasFixedSize(true)
        rcvEntireList.adapter = movieAllAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.entire_list_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> super.onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}