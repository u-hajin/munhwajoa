package com.doremifa.munhwajoa.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface EventDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertEvent(event: Event)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addFavorite(event: Event) {
        event.setFavorite(true)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun deleteFavorite(event: Event) {
        event.setFavorite(false)
    }

    @Query("DELETE FROM event_table WHERE title =:title")
    fun deleteEvent(title: String)

    @Query("SELECT * FROM event_table")
    fun readAllEvent(): List<Event>

    @Query("SELECT * FROM event_table ORDER BY title ASC")
    fun readTitleAsc(): List<Event>

    @Query("SELECT * FROM event_table WHERE favorite = '1'")
    fun readFavorite(): List<Event>

    @Query("SELECT * FROM event_table WHERE codeName =:codeName")
    fun readEventByCodeName(codeName: String): List<Event>

    @Query("SELECT * FROM event_table WHERE title =:title")
    fun readIncludeTitle(title: String): List<Event>
}