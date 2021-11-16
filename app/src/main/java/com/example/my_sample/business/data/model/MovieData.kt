package com.example.my_sample.business.data.model

import com.google.gson.annotations.SerializedName

class MovieData {

    @SerializedName("display_title")
    val title: String? = null

    @SerializedName("mpaa_rating")
    val mpaaRating: String? = null

    @SerializedName("critics_pick")
    val isPicked: Int? = null

    @SerializedName("byline")
    val reviewAuthor: String? = null

    @SerializedName("headline")
    val headline: String? = null

    @SerializedName("summary_short")
    val summaryShort: String? = null

    @SerializedName("publication_date")
    val publicationDate: String? = null

    @SerializedName("opening_date")
    val openingDate: String? = null

    @SerializedName("date_updated")
    val updatedDate: String? = null

    @SerializedName("multimedia")
    val movieMediaData: MovieMediaData? = null

    override fun toString(): String {
        return "MovieData(title=$title, mpaaRating=$mpaaRating, isPicked=$isPicked, reviewAuthor=$reviewAuthor, headline=$headline, summaryShort=$summaryShort, publicationDate=$publicationDate, openingDate=$openingDate, updatedDate=$updatedDate, movieMediaData=$movieMediaData)"
    }
}