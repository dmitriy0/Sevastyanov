package com.example.sevastyanov.domain.usecases

import com.example.sevastyanov.domain.Repository
import com.example.sevastyanov.domain.entities.FilmInList

class GetFavouritesListUseCase(private val repository: Repository) {

    fun getFavouritesList(): List<FilmInList> {
        return repository.getFavouritesList()
    }

}