package com.example.sevastyanov.data

import com.example.sevastyanov.domain.entities.FilmDetails
import com.example.sevastyanov.domain.entities.FilmListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @Headers("X-API-KEY: e30ffed0-76ab-4dd6-b41f-4c9da2b2735b")
    @GET("top/")
    fun getTopList(@Query("type") type: String): Call<FilmListResponse>

    @Headers("X-API-KEY: e30ffed0-76ab-4dd6-b41f-4c9da2b2735b")
    @GET("films/{id}/")
    fun getFilmDetails(@Path("id") id: Int): Call<FilmDetails>

}