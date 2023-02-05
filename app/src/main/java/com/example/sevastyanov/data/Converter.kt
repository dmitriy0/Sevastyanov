package com.example.sevastyanov.data

import com.example.sevastyanov.domain.entities.Genre

class Converter {

    @androidx.room.TypeConverter
    fun stringFromList(genres: List<Genre>): String {
        val list = mutableListOf<String>()
        genres.forEach {
            list.add(it.genre)
        }
        return list.joinToString(",")
    }

    @androidx.room.TypeConverter
    fun listFromString(string: String): List<Genre>{
        val list = string.split(",")
        val genresList = mutableListOf<Genre>()
        list.forEach {
            genresList.add(Genre(it))
        }
        return genresList
    }
}