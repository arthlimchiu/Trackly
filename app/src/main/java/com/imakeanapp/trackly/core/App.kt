package com.imakeanapp.trackly.core

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        AndroidThreeTen.init(this)

        injector = DaggerAppComponent.builder()
            .appModule(AppModule(applicationContext))
            .viewModelModule(ViewModelModule())
            .networkModule(NetworkModule())
            .repositoryModule(RepositoryModule())
            .preferencesModule(PreferencesModule())
            .build()
    }
}

lateinit var injector: AppComponent