package com.example.my_sample.framework.presentation.movie_list.adapter.model

import com.example.my_sample.business.domain.model.Movie

sealed class MovieAdapterItem(val id: String)

data class MovieModel(
    val movie: Movie
): MovieAdapterItem(movie.name + movie.description)

class BottomLoaderModel: MovieAdapterItem("BottomLoaderModel") {
    override fun equals(other: Any?): Boolean {
        return other is BottomLoaderModel
    }

    override fun hashCode(): Int {
        return 7 * toString().hashCode()
    }

    override fun toString(): String {
        return "BottomLoaderModel"
    }
}