package com.example.my_sample.framework.presentation.movie_list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.my_sample.business.domain.model.Movie
import com.example.my_sample.databinding.ItemBottomLoaderBinding
import com.example.my_sample.databinding.ItemMovieBinding
import com.example.my_sample.framework.presentation.movie_list.adapter.model.BottomLoaderModel
import com.example.my_sample.framework.presentation.movie_list.adapter.model.MovieAdapterItem
import com.example.my_sample.framework.presentation.movie_list.adapter.model.MovieModel

class MovieAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MovieAdapterItem>() {

        override fun areItemsTheSame(
            oldItem: MovieAdapterItem,
            newItem: MovieAdapterItem
        ): Boolean {
            if (oldItem::class != newItem::class) return false
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: MovieAdapterItem,
            newItem: MovieAdapterItem
        ): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)

    fun submitList(list: List<Movie>, moreMoviesAvailable: Boolean) {
        val newList = list.map {
            MovieModel(it)
        }.toMutableList<MovieAdapterItem>().also {
            if (moreMoviesAvailable && list.isNotEmpty()) {
                it.add(BottomLoaderModel())
            }
        }
        differ.submitList(newList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType) {
        MOVIE_VIEW_TYPE -> {
            MovieViewHolder(
                ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
        }
        BOTTOM_LOADER_VIEW_TYPE -> {
            BottomLoaderViewHolder(
                ItemBottomLoaderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
        }
        else -> {
            MovieViewHolder(
                ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MovieViewHolder -> {
                holder.bind((differ.currentList[position] as MovieModel).movie)
            }
            is BottomLoaderViewHolder -> {
                holder.bind()
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (differ.currentList[position]) {
            is MovieModel -> {
                MOVIE_VIEW_TYPE
            }
            is BottomLoaderModel -> {
                BOTTOM_LOADER_VIEW_TYPE
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    companion object {
        val MOVIE_VIEW_TYPE = 1
        val BOTTOM_LOADER_VIEW_TYPE = 2
    }
}