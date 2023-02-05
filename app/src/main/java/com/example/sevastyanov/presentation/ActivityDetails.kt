package com.example.sevastyanov.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModelProvider
import com.example.sevastyanov.R
import com.example.sevastyanov.presentation.MainActivity.Companion.FILM_ID_PARAM
import com.squareup.picasso.Picasso

class ActivityDetails : AppCompatActivity() {

    companion object {
        const val LIGHT_ICONS_STATUS_BAR = 0
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.decorView.systemUiVisibility = LIGHT_ICONS_STATUS_BAR

        setContentView(R.layout.activity_details)

        val image = findViewById<ImageView>(R.id.imageView)
        val tvName = findViewById<TextView>(R.id.tvName)
        val tvDescription = findViewById<TextView>(R.id.tvDescription)
        val tvCountry = findViewById<TextView>(R.id.tvCountry)
        val tvGenre = findViewById<TextView>(R.id.tvGenre)

        val mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]

        mainViewModel.getFilmDetails(intent.getIntExtra(FILM_ID_PARAM, -1))

        mainViewModel.filmDetailsLD.observe(this) {
            if (it != null) {
                with(it) {
                    val listGenres = mutableListOf<String>()
                    genres.forEach { genre ->
                        listGenres.add(genre.genre)
                    }
                    tvName.text = nameRu
                    tvDescription.text = description
                    tvCountry.text = countries.joinToString(",")
                    tvGenre.text = listGenres.joinToString { "," }
                    Picasso.get().load(posterUrl).into(image)
                }
            }

        }
    }
}