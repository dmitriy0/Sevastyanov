package com.example.sevastyanov.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModelProvider
import com.example.sevastyanov.R
import com.example.sevastyanov.presentation.MainActivity.Companion.FILM_ID_PARAM
import com.squareup.picasso.Picasso

class ActivityDetails : AppCompatActivity() {

    private lateinit var noInternetImage: ImageView
    private lateinit var noInternetText: TextView
    private lateinit var buttonRepeat: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.decorView.systemUiVisibility = LIGHT_ICONS_STATUS_BAR

        setContentView(R.layout.activity_details)

        val imageBack = findViewById<ImageView>(R.id.image_back)

        val image = findViewById<ImageView>(R.id.imageView)
        val tvName = findViewById<TextView>(R.id.tvName)
        val tvDescription = findViewById<TextView>(R.id.tvDescription)
        val tvCountry = findViewById<TextView>(R.id.tvCountry)
        val tvGenre = findViewById<TextView>(R.id.tvGenre)

        noInternetImage = findViewById(R.id.image_no_internet)
        noInternetText = findViewById(R.id.text_no_internet)
        buttonRepeat = findViewById(R.id.button_repeat)

        val mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]

        mainViewModel.getFilmDetails(intent.getIntExtra(FILM_ID_PARAM, -1))
        mainViewModel.filmDetailsLD.observe(this) {
            if (it == null) {
                noInternetScreen()
            } else {
                defaultScreen()
                with(it) {
                    val listGenres = mutableListOf<String>()
                    val listCountries = mutableListOf<String>()
                    genres.forEach { genre ->
                        listGenres.add(genre.genre)
                    }
                    countries.forEach { country ->
                        listCountries.add(country.country)
                    }
                    tvName.text = nameRu
                    tvDescription.text = description
                    tvCountry.text = "Страны: ${listCountries.joinToString(", ")}"
                    tvGenre.text = "Жанры: ${listGenres.joinToString(", ")}"
                    Picasso.get().load(posterUrl).into(image)
                }
            }
        }

        imageBack.setOnClickListener {
            onBackPressed()
        }

        buttonRepeat.setOnClickListener {
            mainViewModel.getFilmDetails(intent.getIntExtra(FILM_ID_PARAM, -1))
        }
    }

    private fun noInternetScreen() {
        noInternetImage.visibility = View.VISIBLE
        noInternetText.visibility = View.VISIBLE
        buttonRepeat.visibility = View.VISIBLE

    }

    private fun defaultScreen() {
        noInternetImage.visibility = View.INVISIBLE
        noInternetText.visibility = View.INVISIBLE
        buttonRepeat.visibility = View.INVISIBLE
    }

    companion object {
        const val LIGHT_ICONS_STATUS_BAR = 0
    }
}