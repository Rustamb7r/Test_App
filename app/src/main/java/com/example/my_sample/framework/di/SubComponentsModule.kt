package com.example.my_sample.framework.di

import com.example.my_sample.framework.di.main.MainComponent
import dagger.Module

@Module(
    subcomponents = [
        MainComponent::class
    ]
)
class SubComponentsModule