package com.doremifa.munhwajoa.database

import kotlinx.coroutines.flow.Flow

class EventRepository(private val eventDao: EventDao) {

    fun addFavorite(event: Event) {
        eventDao.addFavorite(event)
    }

    fun deleteFavorite(title: String) {
        eventDao.deleteFavorite(title)
    }

    fun readAllFavorite(): Flow<List<Event>> {
        return eventDao.readAllFavorite()
    }

    fun readTitleAsc(): Flow<List<Event>> {
        return eventDao.readTitleAsc()
    }

}