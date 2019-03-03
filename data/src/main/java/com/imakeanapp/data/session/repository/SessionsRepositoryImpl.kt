package com.imakeanapp.data.session.repository

import android.content.SharedPreferences
import com.imakeanapp.data.core.DATE_LAST_VISITED
import com.imakeanapp.data.core.LAST_SCREEN
import com.imakeanapp.data.core.LAST_TRACK_SELECTED
import com.imakeanapp.domain.session.model.Session
import com.imakeanapp.domain.session.repository.SessionsRepository
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class SessionsRepositoryImpl @Inject constructor(
    @Named("TRACKLY_SHARED_PREF") val sharedPref: SharedPreferences
) : SessionsRepository {
    override fun saveSession(session: Session): Completable {
        return Completable
            .fromAction {
                with(sharedPref.edit()) {
                    putString(LAST_SCREEN, session.lastScreen)
                    putLong(LAST_TRACK_SELECTED, session.lastTrackSelected)
                    putString(DATE_LAST_VISITED, session.dateLastVisited)
                    apply()
                }
            }
            .subscribeOn(Schedulers.io())
    }

    override fun getSession(): Single<Session> {
        return Single
            .fromCallable {
                Session(
                    sharedPref.getString(LAST_SCREEN, "")!!,
                    sharedPref.getLong(LAST_TRACK_SELECTED, -1),
                    sharedPref.getString(DATE_LAST_VISITED, "")!!
                )
            }
            .subscribeOn(Schedulers.io())
    }
}