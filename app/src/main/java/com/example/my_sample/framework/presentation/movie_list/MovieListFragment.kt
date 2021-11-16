package com.example.my_sample.framework.presentation.movie_list

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.my_sample.databinding.FragmentMovieListBinding
import com.example.my_sample.framework.base.BaseFragment
import com.example.my_sample.framework.presentation.movie_list.adapter.MovieAdapter
import javax.inject.Inject

const val NOTE_LIST_STATE_BUNDLE_KEY = "MovieListFragment.state"


class MovieListFragment @Inject constructor(
    private val viewModelFactory: ViewModelProvider.Factory
) : BaseFragment<FragmentMovieListBinding>() {

    private val TAG = "MovieListFragment"

    private val viewModel: MovieListViewModel by viewModels {
        viewModelFactory
    }

    private val adapter = MovieAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupOnBackStack()

        setupPaging()

        subscribeObservers()

        binding.swipeRefresh.isRefreshing = true

        setupSwipeRefresh()

        viewModel.fetchMovies()

        restoreInstanceState(savedInstanceState)
    }

    private fun setupOnBackStack() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                activity?.finishAffinity()
            }
        }
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, callback)
    }

    private fun setupSwipeRefresh() {
        binding.swipeRefresh.setOnRefreshListener {
            startNewSearch()
            binding.swipeRefresh.isRefreshing = false
        }
    }

    private fun startNewSearch() {
        viewModel.clearListAndRefresh()
    }

    private fun setupPaging() {
        binding.recyclerView.adapter = adapter

        val llm = binding.recyclerView.layoutManager as? LinearLayoutManager ?: return
        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                try {
                    val viewType = adapter.getItemViewType(llm.findLastVisibleItemPosition())
                    if (viewType == MovieAdapter.BOTTOM_LOADER_VIEW_TYPE) {
                        viewModel.loadMovies()
                    }
                } catch (e: Exception) {
                    Log.w(TAG, e)
                }
            }
        })
    }

    private fun subscribeObservers() {
        viewModel.movieList.observe(viewLifecycleOwner) {
            adapter.submitList(it, viewModel.isMoreMoviesAvailable)
            binding.swipeRefresh.isRefreshing = false
        }
    }

    private fun restoreInstanceState(savedInstanceState: Bundle?) {
        savedInstanceState?.let { inState ->
            (inState[NOTE_LIST_STATE_BUNDLE_KEY] as Bundle?)?.let {
                binding.recyclerView.layoutManager?.onRestoreInstanceState(it)
            }
        }
    }

    // Why didn't I use the "SavedStateHandle" here?
    // It sucks and doesn't work for testing
    override fun onSaveInstanceState(outState: Bundle) {
        val viewState = binding.recyclerView.layoutManager?.onSaveInstanceState()

        outState.putParcelable(
            NOTE_LIST_STATE_BUNDLE_KEY,
            viewState
        )
        super.onSaveInstanceState(outState)
    }
}