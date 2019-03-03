package com.imakeanapp.data.tracks.repository.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [TrackEntity::class], version = 1)
abstract class TracklyDatabase : RoomDatabase() {
    abstract fun trackDao(): TrackDao
}