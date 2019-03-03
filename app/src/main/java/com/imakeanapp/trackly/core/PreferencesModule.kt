package com.imakeanapp.trackly.core

import android.content.Context
import android.content.SharedPreferences
import com.imakeanapp.trackly.R
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class PreferencesModule {

    @Provides
    @Singleton
    @Named("TRACKLY_SHARED_PREF")
    fun providesTracklySharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(context.getString(R.string.trackly_shared_pref), Context.MODE_PRIVATE)
    }
}