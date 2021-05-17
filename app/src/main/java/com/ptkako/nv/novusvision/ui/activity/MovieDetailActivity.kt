package com.ptkako.nv.novusvision.ui.activity

import activityViewBinding
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.ptkako.nv.novusvision.R
import com.ptkako.nv.novusvision.adapter.EpisodeAdapter
import com.ptkako.nv.novusvision.adapter.NumberAdapter
import com.ptkako.nv.novusvision.databinding.ActivityMovieDetailBinding
import com.ptkako.nv.novusvision.model.EpisodeModel

class MovieDetailActivity : AppCompatActivity() {
    private val binding by activityViewBinding(ActivityMovieDetailBinding::inflate)
    private lateinit var episodeAdapter: EpisodeAdapter
    private lateinit var numberAdapter: NumberAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.include2.tlbToolbar)
        supportActionBar!!.title = "Game of Thrones"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        episodeAdapter = EpisodeAdapter(this)
        numberAdapter = NumberAdapter(this)


        val episodeList = ArrayList<EpisodeModel>()
        episodeList.add(EpisodeModel(1, 1, R.drawable.captain_marvel, "Captain Marvel"))
        episodeList.add(EpisodeModel(2, 2, R.drawable.game_of_throne, "Game of Thrones"))
        episodeList.add(EpisodeModel(3, 3, R.drawable.spiderman, "Spiderman"))
        episodeList.add(EpisodeModel(4, 4, R.drawable.superman, "Superman"))
        episodeList.add(EpisodeModel(5, 5, R.drawable.warcraft, "Warcraft"))

        val numberList = ArrayList<String>()
        numberList.add("1")
        numberList.add("2")
        numberList.add("3")
        numberList.add("4")
        numberList.add("5")
        numberList.add(">>")

        numberAdapter.submitList(numberList)
        episodeAdapter.submitList(episodeList)
        setBinding()
    }

    fun setBinding() = with(binding)
    {
        rcvSeasonNumber.setHasFixedSize(true)
        rcvSeasonNumber.adapter = numberAdapter

        rcvEpisode.setHasFixedSize(true)
        rcvEpisode.adapter = episodeAdapter
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> super.onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}