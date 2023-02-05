package com.example.sevastyanov.domain.entities

import androidx.room.*
import com.example.sevastyanov.data.Converter

@Entity
@TypeConverters(Converter::class)
data class FilmInList (
    @PrimaryKey
    var positionInList: Int,
    val filmId: Int,
    val nameRu: String,
    val year: Int,
    val genres: List<Genre>,
    val posterUrlPreview: String,
    var isFavourite: Boolean = false
)