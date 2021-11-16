package com.example.my_sample.framework.presentation.movie_list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.my_sample.business.domain.model.Movie
import com.example.my_sample.business.interactors.GetMovieList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject


class MovieListViewModel @Inject constructor(
    private val getMovieList: GetMovieList
) : ViewModel() {

    private val TAG = "MovieListViewModel"

    private val _movieList: MutableLiveData<List<Movie>> = MutableLiveData()
    val movieList: LiveData<List<Movie>> = _movieList

    var isMoreMoviesAvailable = true

    private var page = 0
    private var isLoading: AtomicBoolean = AtomicBoolean(false)
    private var loadingJob: Job? = null

    fun loadMovies() {
        if (isLoading.get()) return
        isLoading.set(true)
        loadingJob = viewModelScope.launch(Dispatchers.IO) {
            val result = getMovieList.execute(page)

            Log.d(TAG, "result: ${result}")

            isMoreMoviesAvailable = result.hasMore
            if (result.hasMore) {
                page += 1
            }
            Log.d(TAG, "page: ${page}")
            val arrayList = ArrayList<Movie>(_movieList.value ?: emptyList())
            arrayList.addAll(result.movieList)
            _movieList.postValue(arrayList)
            isLoading.set(false)
        }
    }

    fun fetchMovies() {
        if (_movieList.value?.isNullOrEmpty() != false) {
            loadMovies()
        }
    }

    fun clearListAndRefresh() {
        isLoading.set(false)
        _movieList.postValue(emptyList())
        page = 0
        loadMovies()
    }

    override fun onCleared() {
        super.onCleared()
        loadingJob?.cancel()
        loadingJob = null
    }
}