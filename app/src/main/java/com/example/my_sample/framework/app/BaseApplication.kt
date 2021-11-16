package com.example.my_sample.framework.app

import android.app.Application
import com.example.my_sample.framework.di.ApplicationComponent
import com.example.my_sample.framework.di.DaggerApplicationComponent

class BaseApplication : Application() {

    private var appComponent: ApplicationComponent = DaggerApplicationComponent
        .factory()
        .create(
            application = this,
            applicationContext = this
        )

    fun getAppComponent(): ApplicationComponent {
        return appComponent
    }

}