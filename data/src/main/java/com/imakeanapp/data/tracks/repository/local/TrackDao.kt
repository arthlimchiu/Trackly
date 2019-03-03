package com.imakeanapp.data.tracks.repository.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TrackDao {

    @Query("SELECT * FROM tracks ORDER BY createdAt")
    fun getTracks(): List<TrackEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(track: TrackEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(tracks: List<TrackEntity>)
}