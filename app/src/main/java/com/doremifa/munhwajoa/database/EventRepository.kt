package com.doremifa.munhwajoa.database

import kotlinx.coroutines.flow.Flow

class EventRepository(private val eventDao: EventDao) {

    fun insertEvent(event: Event) {
        eventDao.insertEvent(event)
    }

    fun addFavorite(event: Event) {
        eventDao.addFavorite(event)
    }

    fun deleteEvent(title: String) {
        eventDao.deleteEvent(title)
    }

    fun readAllEvent(): Flow<List<Event>> {
        return eventDao.readAllEvent()
    }

    fun readTitleAsc(): Flow<List<Event>> {
        return eventDao.readTitleAsc()
    }

    fun readFavorite(): Flow<List<Event>> {
        return eventDao.readFavorite()
    }

    fun readEventByCodeName(codeName: String): Flow<List<Event>> {
        return eventDao.readEventByCodeName(codeName)
    }

    fun readIncludeTitle(title: String): Flow<List<Event>> {
        return eventDao.readIncludeTitle(title)
    }

}