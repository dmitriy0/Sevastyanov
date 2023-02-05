package com.example.sevastyanov.data

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.sevastyanov.domain.entities.FilmInList

@androidx.room.Dao
interface Dao {

    @Query("SELECT * FROM FilmInList")
    fun getFavouritesList(): List<FilmInList>?

    @Insert(onConflict = REPLACE)
    fun insert(filmInList: FilmInList)

    @Delete
    fun delete(filmInList: FilmInList)

}