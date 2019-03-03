package com.imakeanapp.trackly.core

import com.imakeanapp.data.session.repository.SessionsRepositoryImpl
import com.imakeanapp.data.tracks.repository.TracksRepositoryImpl
import com.imakeanapp.domain.session.usecase.GetSession
import com.imakeanapp.domain.session.usecase.SaveSession
import com.imakeanapp.domain.tracks.usecase.GetTracks
import com.imakeanapp.trackly.tracks.view_model.TracksVMFactory
import dagger.Module
import dagger.Provides

@Module
class ViewModelModule {

    @Provides
    fun providesMainVMFactory(tracksRepository: TracksRepositoryImpl,
                              sessionsRepository: SessionsRepositoryImpl): TracksVMFactory {
        return TracksVMFactory(
            GetTracks(tracksRepository),
            SaveSession(sessionsRepository),
            GetSession(sessionsRepository)
        )
    }
}