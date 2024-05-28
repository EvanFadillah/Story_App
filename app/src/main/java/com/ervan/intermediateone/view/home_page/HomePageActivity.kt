package com.ervan.intermediateone.view.home_page

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ervan.intermediateone.R
import com.ervan.intermediateone.view.ViewModelFactories.StoryViewModelFactory
import com.ervan.intermediateone.databinding.ActivityHomePageBinding
import com.ervan.intermediateone.view.add.AddStoryActivity
import com.ervan.intermediateone.view.detail.DetailActivity
import com.ervan.intermediateone.view.maps.MapsActivity
import com.ervan.intermediateone.view.welcome.WelcomeActivity

class HomePageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomePageBinding

    private val adapter by lazy {
        ListStoriesAdapter { story ->
            Intent(this, DetailActivity::class.java).apply {
                putExtra(DetailActivity.EXTRA_STORIES, story)
                startActivity(this)
            }

        }
    }

    private val viewModel by viewModels<HomePageViewModel> {
        StoryViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomePageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.homePage_title)

        binding.rvStories.layoutManager = LinearLayoutManager(this)
        binding.rvStories.setHasFixedSize(true)

        binding.progressBar.visibility = View.VISIBLE
        getData()


        binding.fabBtn.setOnClickListener {
            val intent = Intent(this, AddStoryActivity::class.java)
            startActivity(intent)
        }

    }

    private fun getData() {
        binding.rvStories.adapter = adapter
        viewModel.stories.observe(this, {
            binding.progressBar.visibility = View.GONE
            adapter.submitData(lifecycle,it)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.main_menu) {
            viewModel.logout()
            val intent = Intent(this, WelcomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            return true
        }
        else if (item.itemId == R.id.test_maps) {
            val intent = Intent (this, MapsActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}