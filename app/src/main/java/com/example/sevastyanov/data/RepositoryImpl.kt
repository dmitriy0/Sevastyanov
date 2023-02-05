package com.example.sevastyanov.data

import android.util.Log
import com.example.sevastyanov.App
import com.example.sevastyanov.domain.Repository
import com.example.sevastyanov.domain.entities.FilmDetails
import com.example.sevastyanov.domain.entities.FilmInList
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

object RepositoryImpl : Repository {

    private const val BASE_URL_LIST = "https://kinopoiskapiunofficial.tech/api/v2.2/films/"
    private const val BASE_URL_DETAILS = "https://kinopoiskapiunofficial.tech/api/v2.2/"
    private val dao = App.instance?.getDatabase()?.dao()
    var list: SortedSet<FilmInList> =
        sortedSetOf<FilmInList>({ o1, o2 -> o1.positionInList.compareTo(o2.positionInList)})

    override fun addFilmToFavourites(filmInList: FilmInList): FilmDetails? {
        dao?.insert(filmInList)

        list.remove(filmInList)
        list.add(filmInList.copy(isFavourite = true))


        val filmDetails = getFilmDetails(filmInList.filmId)

        if (filmDetails != null) {
            dao?.insertFavourite(filmDetails)
            return filmDetails
        } else {
            return null
        }

    }

    override fun deleteFilmFromFavourites(filmInList: FilmInList) {
        dao?.delete(filmInList)
        val filmDetails = dao?.getFilmDetails(filmInList.filmId)
        if (filmDetails != null) {
            dao?.deleteFavourites(filmDetails)
        }
        list.remove(filmInList)
        list.add(filmInList.copy(isFavourite = false))
    }

    override fun getFavouritesList(): List<FilmInList> {
        val favouritesList = mutableListOf<FilmInList>()
        dao?.getFavouritesList()?.forEach {
            favouritesList.add(it.copy(isFavourite = true))
        }
        return favouritesList
    }

    override fun getFilmDetails(filmId: Int): FilmDetails? {
        val filmDetails = dao?.getFilmDetails(filmId)

        val favouriteFilm = dao?.getFavouriteFilm(filmId)

        if (filmDetails != null) {
            return filmDetails
        } else {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL_DETAILS)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val service = retrofit.create(ApiService::class.java)
            val call = service.getFilmDetails(filmId)
            return try {
                val fDetails = call.execute().body()
                if (favouriteFilm != null) {
                    dao?.insertFavourite(fDetails)
                }
                fDetails
            } catch (e: Exception) {
                null
            }
        }
    }

    override fun getTopList(): List<FilmInList>? {
        if (list.isEmpty()) {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL_LIST)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val service = retrofit.create(ApiService::class.java)
            val call = service.getTopList("TOP_100_POPULAR_FILMS")
            val listResponse: List<FilmInList>
            try {
                listResponse = call.execute().body().films.toMutableList()
            } catch (e: Exception) {
                return null
            }
            for (i in listResponse.indices) {
                listResponse[i].positionInList = i
            }
            list = listResponse.toSortedSet { o1, o2 -> o1.positionInList.compareTo(o2.positionInList) }
        }
        getFavouritesList().forEach {
            addFilmToFavourites(it)
        }
        return list.toList()
    }
}