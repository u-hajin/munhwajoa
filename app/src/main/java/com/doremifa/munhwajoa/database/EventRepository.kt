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

    fun readAllEvent(): List<Event> {
        return eventDao.readAllEvent()
    }

    fun readTitleAsc(): List<Event> {
        return eventDao.readTitleAsc()
    }

    fun readFavorite(): List<Event> {
        return eventDao.readFavorite()
    }

    fun readEventByCodeName(codeName: String): List<Event> {
        return eventDao.readEventByCodeName(codeName)
    }

    fun readIncludeTitle(title: String): List<Event> {
        return eventDao.readIncludeTitle(title)
    }

}