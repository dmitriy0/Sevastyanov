package com.example.sevastyanov.domain.usecases

import com.example.sevastyanov.domain.Repository
import com.example.sevastyanov.domain.entities.FilmDetails
import com.example.sevastyanov.domain.entities.FilmInList

class GetFilmDetailsUseCase(private val repository: Repository) {

    fun getFilmDetails(filmId: Int): FilmDetails {
        return repository.getFilmDetails(filmId)
    }

}