package com.imakeanapp.trackly.core

import android.content.SharedPreferences
import com.google.gson.Gson
import com.imakeanapp.data.core.ItunesApi
import com.imakeanapp.data.tracks.repository.TracksRepositoryImpl
import com.imakeanapp.data.tracks.repository.local.TracklyDatabase
import com.imakeanapp.data.tracks.repository.local.TracksLocalDataStoreImpl
import com.imakeanapp.trackly.tracks.view_model.TracksVMFactory
import dagger.Component
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Component(
    modules = [
        NetworkModule::class,
        AppModule::class,
        ViewModelModule::class,
        PreferencesModule::class,
        RepositoryModule::class
    ]
)
@Singleton
interface AppComponent {

    fun mainVMFactory(): TracksVMFactory

    fun retrofit(): Retrofit

    fun itunesApi(): ItunesApi

    fun gson(): Gson

    @Named("TRACKLY_SHARED_PREF")
    fun tracklySharedPreferences(): SharedPreferences

    fun tracksRepository(): TracksRepositoryImpl

    fun tracklyDatabase(): TracklyDatabase

    fun tracksLocalDatastore(): TracksLocalDataStoreImpl
}