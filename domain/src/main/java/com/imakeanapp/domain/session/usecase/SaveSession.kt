package com.imakeanapp.domain.session.usecase

import com.imakeanapp.domain.core.CompletableWithParamUseCase
import com.imakeanapp.domain.session.model.Session
import com.imakeanapp.domain.session.repository.SessionsRepository
import io.reactivex.Completable

/**
 * A single unit of action that saves the user's state in a form a session
 * to an instance of SessionsRepository.
 */
class SaveSession(private val repository: SessionsRepository) : CompletableWithParamUseCase<Session> {
    override fun execute(t: Session): Completable = repository.saveSession(t)
}