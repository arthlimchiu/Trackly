package com.imakeanapp.trackly.core

import android.content.Context
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val context: Context) {

    @Provides
    fun providesAppContext(): Context = context

    @Provides
    @Singleton
    fun providesGson(): Gson {
        return Gson()
    }
}