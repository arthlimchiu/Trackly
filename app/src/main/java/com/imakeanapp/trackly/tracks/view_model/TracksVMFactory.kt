package com.imakeanapp.trackly.tracks.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.imakeanapp.domain.session.usecase.GetSession
import com.imakeanapp.domain.session.usecase.SaveSession
import com.imakeanapp.domain.tracks.usecase.GetTracks

class TracksVMFactory(private val getTracks: GetTracks,
                      private val saveSession: SaveSession,
                      private val getSession: GetSession) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TracksViewModel(getTracks, saveSession, getSession) as T
    }
}