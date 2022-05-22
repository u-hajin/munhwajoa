package com.doremifa.munhwajoa.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface EventDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addFavorite(event: Event)

    @Query("DELETE FROM favorite_table WHERE title =:title")
    fun deleteFavorite(title: String)

    @Query("SELECT * FROM favorite_table")
    fun readAllFavorite(): Flow<List<Event>>

    @Query("SELECT * FROM favorite_table ORDER BY title ASC")
    fun readTitleAsc(): Flow<List<Event>>

}