package com.imakeanapp.domain.session.model

data class Session(
    val lastScreen: String,
    val lastTrackSelected: Long,
    val dateLastVisited: String
)