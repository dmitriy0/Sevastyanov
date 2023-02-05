package com.example.sevastyanov.presentation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.sevastyanov.R
import com.example.sevastyanov.presentation.MainActivity.Companion.FILM_ID_PARAM
import com.squareup.picasso.Picasso


class FragmentDetails : Fragment() {

    private var filmId: Int = -1

    private lateinit var noInternetImage: ImageView
    private lateinit var noInternetText: TextView
    private lateinit var buttonRepeat: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        filmId = requireArguments().getInt(FILM_ID_PARAM)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val image = view.findViewById<ImageView>(R.id.imageView)
        val tvName = view.findViewById<TextView>(R.id.tvName)
        val tvDescription = view.findViewById<TextView>(R.id.tvDescription)
        val tvCountry = view.findViewById<TextView>(R.id.tvCountry)
        val tvGenre = view.findViewById<TextView>(R.id.tvGenre)

        noInternetImage = view.findViewById(R.id.image_no_internet)
        noInternetText = view.findViewById(R.id.text_no_internet)
        buttonRepeat = view.findViewById(R.id.button_repeat)

        val mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]

        mainViewModel.getFilmDetails(filmId)

        mainViewModel.filmDetailsLD.observe(viewLifecycleOwner) {
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
                    tvCountry.text = listCountries.joinToString(", ")
                    tvGenre.text = listGenres.joinToString(", ")
                    Picasso.get().load(posterUrl).into(image)
                }
            }
        }

        buttonRepeat.setOnClickListener {
            mainViewModel.getFilmDetails(filmId)
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

}