package com.example.sevastyanov.data

import com.example.sevastyanov.domain.entities.Country
import com.example.sevastyanov.domain.entities.Genre

class Converter {

    @androidx.room.TypeConverter
    fun stringFromListGenre(genres: List<Genre>): String {
        val list = mutableListOf<String>()
        genres.forEach {
            list.add(it.genre)
        }
        return list.joinToString(",")
    }

    @androidx.room.TypeConverter
    fun listGenreFromString(string: String): List<Genre>{
        val list = string.split(",")
        val genresList = mutableListOf<Genre>()
        list.forEach {
            genresList.add(Genre(it))
        }
        return genresList
    }

    @androidx.room.TypeConverter
    fun stringFromListCountry(countries: List<Country>): String {
        val list = mutableListOf<String>()
        countries.forEach {
            list.add(it.country)
        }
        return list.joinToString(",")
    }

    @androidx.room.TypeConverter
    fun listCountryFromString(string: String): List<Country>{
        val list = string.split(",")
        val countriesList = mutableListOf<Country>()
        list.forEach {
            countriesList.add(Country(it))
        }
        return countriesList
    }
}