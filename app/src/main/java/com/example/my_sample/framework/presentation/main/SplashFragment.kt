package com.example.my_sample.framework.presentation.main

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.my_sample.databinding.FragmentSplashBinding
import com.example.my_sample.framework.base.BaseFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

class SplashFragment @Inject constructor(
    private val viewModelFactory: ViewModelProvider.Factory
) : BaseFragment<FragmentSplashBinding>() {

    val viewModel: SplashViewModel by viewModels {
        viewModelFactory
    }

    private var timer: Job? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        timer = startCoroutineTimer(delayMillis = 2000) {
            navMovieListFragment()
        }
        lifecycleScope.launch {
            timer?.join()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        timer?.cancel()
        timer = null
    }

    private fun navMovieListFragment() = lifecycleScope.launch(Dispatchers.Main) {
        try {
            val direction = SplashFragmentDirections.actionSplashToMovieList()
            findNavController().navigate(direction)
        } catch (e: Exception) {
            Log.w("SplashFragment", e)
        }
    }

    private inline fun startCoroutineTimer(
        delayMillis: Long = 0,
        crossinline action: () -> Unit
    ) = lifecycleScope.launch(Dispatchers.IO) {
        delay(delayMillis)
        action()
    }
}