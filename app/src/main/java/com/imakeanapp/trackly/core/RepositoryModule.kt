package com.imakeanapp.trackly.core

import android.content.Context
import androidx.room.Room
import com.imakeanapp.data.tracks.repository.local.TracklyDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideTracklyDatabase(context: Context): TracklyDatabase {
        return Room.databaseBuilder(context, TracklyDatabase::class.java, "trackly.db").build()
    }
}