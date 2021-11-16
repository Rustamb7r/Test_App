package com.example.my_sample.business.interactors

import android.util.Log
import com.example.my_sample.BuildConfig
import com.example.my_sample.business.data.MovieService
import com.example.my_sample.business.data.model.toMovieList
import com.example.my_sample.business.domain.model.MoviesObject
import javax.inject.Inject
import javax.inject.Singleton

private const val MOVIES_PER_PAGE = 20


@Singleton
class GetMovieList @Inject constructor(private val service: MovieService) {


    private val TAG = "GetMovieList"

    suspend fun execute(page: Int): MoviesObject {
        try {
            val movieResponse =
                service.getMovieList(
                    apiKey = BuildConfig.API_KEY,
                    offset = page * MOVIES_PER_PAGE
                ).body()

            return MoviesObject(
                movieList = movieResponse?.toMovieList() ?: emptyList(),
                hasMore = movieResponse?.hasMore ?: false
            )

        } catch (e: Exception) {
            Log.w(TAG, e)
            return MoviesObject(
                movieList = emptyList(),
                hasMore = false
            )
        }
    }
}