package com.doremifa.munhwajoa.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface EventDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertEvent(event: Event)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addFavorite(event: Event)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun deleteFavorite(event: Event)

    @Query("SELECT * FROM event_table ORDER BY title ASC")
    fun readAllEvent(): List<Event>

    @Query("SELECT * FROM event_table WHERE favorite = '1'")
    fun readFavorite(): List<Event>

    @Query("SELECT * FROM event_table WHERE codeName =:codeName ORDER BY title ASC")
    fun readEventByCodeName(codeName: String): List<Event>

    @Query("SELECT * FROM event_table ORDER BY startDate ASC")
    fun readAllEventOldest(): List<Event>

    @Query("SELECT * FROM event_table ORDER BY startDate DESC")
    fun readAllEventNewest(): List<Event>

    @Query("SELECT * FROM event_table WHERE codeName =:codeName ORDER BY startDate ASC")
    fun readEventOldest(codeName: String): List<Event>

    @Query("SELECT * FROM event_table WHERE codeName =:codeName ORDER BY startDate DESC")
    fun readEventNewest(codeName: String): List<Event>

    @Query("SELECT * FROM event_table WHERE fee LIKE :fee")
    fun readAllEventFree(fee: String): List<Event>

    @Query("SELECT * FROM event_table WHERE codeName =:codeName AND fee LIKE :fee")
    fun readEventFree(codeName: String, fee: String): List<Event>
}