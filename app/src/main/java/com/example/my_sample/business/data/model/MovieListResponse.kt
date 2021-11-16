package com.example.my_sample.business.data.model

import android.util.Log
import com.example.my_sample.business.domain.model.Movie
import com.google.gson.annotations.SerializedName

class MovieListResponse() {

    @SerializedName("status")
    val status: String? = null

    @SerializedName("copyright")
    val copyright: String? = null

    @SerializedName("has_more")
    val hasMore: Boolean = false

    @SerializedName("num_results")
    val num_results: Int? = null

    @SerializedName("results")
    val movieList: List<MovieData>? = null

    override fun toString(): String {
        return "MovieListResponse(status=$status, copyright=$copyright, has_more=$hasMore, num_results=$num_results, movieList=$movieList)"
    }

}

fun MovieListResponse?.toMovieList(): List<Movie> {
    return this?.movieList.toMovies()
}

private fun List<MovieData>?.toMovies(): List<Movie> {
    return this?.map {
        Movie(
            name = it.title ?: "",
            it.summaryShort ?: "",
            imageUrl = it.movieMediaData?.imageUrl ?: ""
        )
    } ?: emptyList()
}
