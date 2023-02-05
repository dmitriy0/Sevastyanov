package com.example.sevastyanov.domain.usecases

import com.example.sevastyanov.domain.Repository
import com.example.sevastyanov.domain.entities.FilmInList

class DeleteFilmFromFavouritesUseCase(private val repository: Repository) {

    fun deleteFilmFromFavourites(filmInList: FilmInList) {
        repository.deleteFilmFromFavourites(filmInList)
    }

}