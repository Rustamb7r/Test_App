package com.example.my_sample.framework.presentation.movie_list.adapter

import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.my_sample.R
import com.example.my_sample.business.domain.model.Movie
import com.example.my_sample.databinding.ItemMovieBinding

class MovieViewHolder(
    private val binding: ItemMovieBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(movie: Movie) {
        binding.titleView.text = movie.name
        binding.descriptionView.text = movie.description
        loadImage(
            view = binding.imageView,
            url = movie.imageUrl,
            placeholder = R.drawable.ic_item_placeholder
        )
    }

    private fun loadImage(view: ImageView, url: String?, @DrawableRes placeholder: Int? = null) =
        Glide.with(view).load(url)
            .fitCenter()
            .let { request ->
                if (placeholder != null) {
                    request.placeholder(placeholder)
                }
                request.into(view)
            }
}