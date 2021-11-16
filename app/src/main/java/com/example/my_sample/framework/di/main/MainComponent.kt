package com.example.my_sample.framework.di.main

import com.example.my_sample.framework.presentation.main.multifeature.MainNavHostFragment
import dagger.Subcomponent

@MainFragmentScope
@Subcomponent(
    modules = [
        ViewModelModule::class,
        MainFragmentBuildersModule::class
    ]
)
interface MainComponent {

    @Subcomponent.Factory
    interface Factory {

        fun create(): MainComponent
    }

    fun inject(mainNavHostFragment: MainNavHostFragment)

}