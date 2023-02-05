package com.example.sevastyanov.domain.usecases

import com.example.sevastyanov.domain.Repository
import com.example.sevastyanov.domain.entities.FilmInList

class GetTopListUseCase(private val repository: Repository) {

    fun getTopList(): List<FilmInList> {
        return repository.getTopList()
    }

}