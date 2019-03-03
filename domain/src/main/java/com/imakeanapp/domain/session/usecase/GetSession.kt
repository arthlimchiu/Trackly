package com.imakeanapp.domain.session.usecase

import com.imakeanapp.domain.core.SingleUseCase
import com.imakeanapp.domain.session.model.Session
import com.imakeanapp.domain.session.repository.SessionsRepository
import io.reactivex.Single

/**
 * A single unit of action that gets the session that represents a user's state
 * from an instance of SessionsRepository.
 */
class GetSession(private val repository: SessionsRepository) : SingleUseCase<Session> {
    override fun execute(): Single<Session> = repository.getSession()
}