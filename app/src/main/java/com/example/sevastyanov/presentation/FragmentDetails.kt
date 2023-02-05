package com.example.sevastyanov.presentation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.sevastyanov.R
import com.example.sevastyanov.presentation.MainActivity.Companion.FILM_ID_PARAM
import com.squareup.picasso.Picasso


class FragmentDetails : Fragment() {

    private var filmId: Int = -1

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

        val mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]

        mainViewModel.getFilmDetails(filmId)

        mainViewModel.filmDetailsLD.observe(viewLifecycleOwner) {
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