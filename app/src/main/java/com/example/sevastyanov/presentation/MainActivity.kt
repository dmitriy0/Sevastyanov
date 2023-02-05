package com.example.sevastyanov.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
import android.widget.Button
import android.widget.LinearLayout
import androidx.core.view.WindowCompat
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.sevastyanov.R

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FilmListAdapter
    private lateinit var viewModel: MainViewModel
    private var screenMode = SCREEN_MODE_TOP
    private var fragmentContainer: LinearLayout? = null

    private var filmId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContentView(R.layout.activity_main)

        fragmentContainer = findViewById(R.id.fragment_container)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        setupRecyclerView()
        viewModel.getTopList()

        val buttonPopular = findViewById<Button>(R.id.button_popular)
        val buttonFavourites = findViewById<Button>(R.id.button_favourites)

        viewModel.topListLD.observe(this) {
            adapter.topList = it
        }

        buttonFavourites.setOnClickListener {
            viewModel.getFavouritesList()
            screenMode = SCREEN_MODE_FAVOURITES
        }

        buttonPopular.setOnClickListener {
            viewModel.getTopList()
            screenMode = SCREEN_MODE_TOP
        }

    }

    private fun setupRecyclerView() {
        recyclerView = findViewById(R.id.recycler_view)
        adapter = FilmListAdapter()
        recyclerView.adapter = adapter

        adapter.onItemLongClick = {
            viewModel.addOrDelete(it, screenMode)
        }

        adapter.onItemClick = {
            if (isOnePaneMode()) {
                val intent = Intent(this, ActivityDetails::class.java)
                intent.putExtra(FILM_ID_PARAM, it.filmId)
                startActivity(intent)
            } else {
                if (filmId != it.filmId) {
                    filmId = it.filmId
                    val fragment = FragmentDetails()
                    fragment.apply {
                        arguments = Bundle().apply {
                            putInt(FILM_ID_PARAM, it.filmId)
                        }
                    }
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .addToBackStack(null)
                        .commit()
                }

            }

        }
    }

    private fun isOnePaneMode(): Boolean {
        return fragmentContainer == null
    }

    companion object {
        const val SCREEN_MODE_TOP = 0
        const val SCREEN_MODE_FAVOURITES = 1
        const val FILM_ID_PARAM = "filmId"
    }
}