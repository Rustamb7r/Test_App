package com.example.my_sample.business.domain.model

data class MoviesObject(
    val movieList: List<Movie>,
    val hasMore: Boolean = false
)