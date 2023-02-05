package com.example.sevastyanov.domain.usecases

import com.example.sevastyanov.domain.Repository
import com.example.sevastyanov.domain.entities.FilmInList

class AddFilmToFavouritesUseCase(private val repository: Repository) {

    fun addFilmToFavourites(filmInList: FilmInList) {
        repository.addFilmToFavourites(filmInList)
    }

}