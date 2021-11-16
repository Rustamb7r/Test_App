package com.example.my_sample.business.data

import com.example.my_sample.business.data.model.MovieListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    @GET("/svc/movies/v2/reviews/{type}.json")
    suspend fun getMovieList(
        @Path("type") type: String = "all",
        @Query("api-key") apiKey: String,
        @Query("offset") offset: Int
    ): Response<MovieListResponse>

}