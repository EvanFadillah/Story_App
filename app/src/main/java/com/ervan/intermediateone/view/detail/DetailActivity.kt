package com.ervan.intermediateone.view.detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import coil.load
import com.ervan.intermediateone.R
import com.ervan.intermediateone.data.responses.ListStoryItem
import com.ervan.intermediateone.databinding.ActivityDetailBinding
import com.ervan.intermediateone.view.home_page.HomePageActivity
import com.ervan.intermediateone.view.home_page.ListStoriesAdapter

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val stories: ListStoryItem? = intent.getParcelableExtra(EXTRA_STORIES)

        setStories(stories)

        supportActionBar?.apply {
            title = getString(R.string.detail_title)
            setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            val intent = Intent(this, HomePageActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setStories(item: ListStoryItem?) {
        binding.apply {
            tvDetailName.text = item?.name
            tvDetailDesc.text = item?.description
            tvDetailDate.text = ListStoriesAdapter.dateToString(item?.createdAt)
            ivDetail.load(item?.photoUrl)
        }

    }

    companion object {
        const val EXTRA_STORIES = "extra_stories"
    }
}
