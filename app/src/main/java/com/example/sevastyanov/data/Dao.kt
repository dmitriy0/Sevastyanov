package com.example.sevastyanov.data

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.sevastyanov.domain.entities.FilmDetails
import com.example.sevastyanov.domain.entities.FilmInList

@androidx.room.Dao
interface Dao {

    @Query("SELECT * FROM FilmInList")
    fun getFavouritesList(): List<FilmInList>?

    @Query("SELECT * FROM FilmInList WHERE filmId = :id")
    fun getFavouriteFilm(id: Int): FilmInList?

    @Insert(onConflict = REPLACE)
    fun insert(filmInList: FilmInList)

    @Delete
    fun delete(filmInList: FilmInList)

    @Insert(onConflict = REPLACE)
    fun insertFavourite(filmDetails: FilmDetails)

    @Delete
    fun deleteFavourites(filmDetails: FilmDetails)

    @Query("SELECT * FROM FilmDetails WHERE kinopoiskId = :filmId")
    fun getFilmDetails(filmId: Int): FilmDetails?

}