package com.example.my_sample.framework.di.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.example.my_sample.framework.di.main.keys.MainFragmentKey
import com.example.my_sample.framework.presentation.main.multifeature.MainFragmentFactory
import com.example.my_sample.framework.presentation.main.SplashFragment
import com.example.my_sample.framework.presentation.movie_list.MovieListFragment
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MainFragmentBuildersModule {

    @Binds
    abstract fun bindFragmentFactory(mainFragmentFactory: MainFragmentFactory): FragmentFactory

    @Binds
    @IntoMap
    @MainFragmentKey(SplashFragment::class)
    abstract fun bindSplashFragment(splashFragment: SplashFragment): Fragment

    @Binds
    @IntoMap
    @MainFragmentKey(MovieListFragment::class)
    abstract fun bindMovieListFragment(splashFragment: MovieListFragment): Fragment

}