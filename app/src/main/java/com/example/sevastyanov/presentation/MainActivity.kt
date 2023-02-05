package com.example.sevastyanov.presentation

import android.content.Intent
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
import android.view.WindowManager
import android.widget.*
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.WindowCompat
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.sevastyanov.R
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FilmListAdapter
    private lateinit var viewModel: MainViewModel
    private var screenMode = SCREEN_MODE_TOP
    private var fragmentContainer: LinearLayout? = null
    private lateinit var buttonTop: Button
    private lateinit var buttonFavourites: Button
    private lateinit var title: TextView

    private lateinit var noInternetImage: ImageView
    private lateinit var noInternetText: TextView
    private lateinit var buttonRepeat: Button

    private lateinit var imageSearch: ImageView

    private var filmId = UNDEFINED_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContentView(R.layout.activity_main)

        fragmentContainer = findViewById(R.id.fragment_container)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        buttonTop = findViewById(R.id.button_top)
        buttonFavourites = findViewById(R.id.button_favourites)
        title = findViewById(R.id.title)

        noInternetImage = findViewById(R.id.image_no_internet)
        noInternetText = findViewById(R.id.text_no_internet)
        buttonRepeat = findViewById(R.id.button_repeat)

        imageSearch = findViewById(R.id.imageView2)

        setupRecyclerView()
        viewModel.getTopList()

        viewModel.topListLD.observe(this) {
            if (it == null) {
                noInternetScreen()
            } else {
                defaultScreen()
                adapter.topList = it
            }
        }

        viewModel.isAddedSuccessfully.observe(this) {
            if (it == null) {
                Toast.makeText(
                    this,
                    "Не удалось сохранить описание фильма, отсутствует интернет соединение",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        buttonFavourites.setOnClickListener {
            viewModel.getFavouritesList()
            changeToFavourites()
        }

        buttonTop.setOnClickListener {
            viewModel.getTopList()
            changeToTop()
        }

        buttonRepeat.setOnClickListener {
            viewModel.getTopList()
        }

        imageSearch.setOnClickListener {
            Toast.makeText(this, "На данный момент поиск еще не работает :(", Toast.LENGTH_LONG).show()
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

    private fun changeToTop() {
        screenMode = SCREEN_MODE_TOP
        title.text = TITLE_TOP
        buttonFavourites.setBackgroundResource(R.drawable.button_dark)
        buttonTop.setBackgroundResource(R.drawable.button_light)
        buttonFavourites.setTextColor(getColor(R.color.white))
        buttonTop.setTextColor(getColor(R.color.light_button_text_color))
    }

    private fun changeToFavourites() {
        screenMode = SCREEN_MODE_FAVOURITES
        title.text = TITLE_FAVOURITES
        buttonTop.setBackgroundResource(R.drawable.button_dark)
        buttonFavourites.setBackgroundResource(R.drawable.button_light)
        buttonTop.setTextColor(getColor(R.color.white))
        buttonFavourites.setTextColor(getColor(R.color.light_button_text_color))
    }

    private fun noInternetScreen() {
        noInternetImage.visibility = View.VISIBLE
        noInternetText.visibility = View.VISIBLE
        buttonRepeat.visibility = View.VISIBLE

        recyclerView.visibility = View.INVISIBLE
    }

    private fun defaultScreen() {
        noInternetImage.visibility = View.INVISIBLE
        noInternetText.visibility = View.INVISIBLE
        buttonRepeat.visibility = View.INVISIBLE

        recyclerView.visibility = View.VISIBLE
    }

    companion object {
        const val SCREEN_MODE_TOP = 0
        const val SCREEN_MODE_FAVOURITES = 1
        const val FILM_ID_PARAM = "filmId"
        const val UNDEFINED_ID = -1
        const val TITLE_TOP = "Популярные"
        const val TITLE_FAVOURITES = "Избранное"
    }
}