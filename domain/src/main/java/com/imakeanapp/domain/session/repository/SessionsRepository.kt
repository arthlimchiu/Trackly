package com.imakeanapp.domain.session.repository

import com.imakeanapp.domain.session.model.Session
import io.reactivex.Completable
import io.reactivex.Single

/**
 * This interface should be implemented by any class whose responsibility
 * is to manage user-related information in a form of a session.
 */
interface SessionsRepository {

    /**
     * Save user-related information in a form of a session which
     * represents a user's state.
     *
     * @return a Completable that emits to either onComplete() or onError()
     */
    fun saveSession(session: Session): Completable

    /**
     * Get the user's session that represents a user's state.
     *
     * @return a Single that emits the user's session
     */
    fun getSession(): Single<Session>
}