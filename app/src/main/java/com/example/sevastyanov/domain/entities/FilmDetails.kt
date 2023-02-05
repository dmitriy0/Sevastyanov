package com.example.sevastyanov.domain.entities

data class FilmDetails(
    val kinopoiskId: Int,
    val nameRu: String,
    val countries: List<Country>,
    val genres: List<Genre>,
    val posterUrl: String,
    val description: String
)
