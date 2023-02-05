package com.example.sevastyanov.domain

import com.example.sevastyanov.domain.entities.FilmDetails
import com.example.sevastyanov.domain.entities.FilmInList

interface Repository {

    fun addFilmToFavourites(filmInList: FilmInList): FilmDetails?
    fun deleteFilmFromFavourites(filmInList: FilmInList)
    fun getFavouritesList(): List<FilmInList>
    fun getFilmDetails(filmId: Int): FilmDetails?
    fun getTopList(): List<FilmInList>?
}