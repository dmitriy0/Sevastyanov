package com.example.sevastyanov.domain.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.sevastyanov.data.Converter

@Entity
@TypeConverters(Converter::class)
data class FilmDetails(
    @PrimaryKey
    val kinopoiskId: Int,
    val nameRu: String,
    val countries: List<Country>,
    val genres: List<Genre>,
    val posterUrl: String,
    val description: String
)
