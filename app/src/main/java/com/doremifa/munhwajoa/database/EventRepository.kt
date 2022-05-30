package com.doremifa.munhwajoa.database

import androidx.room.Query
import kotlinx.coroutines.flow.Flow

class EventRepository(private val eventDao: EventDao) {

    fun insertEvent(event: Event) {
        eventDao.insertEvent(event)
    }

    fun addFavorite(event: Event) {
        eventDao.addFavorite(event)
    }

    fun deleteFavorite(event: Event) {
        eventDao.deleteFavorite(event)
    }

    fun readAllEvent(): List<Event> {
        return eventDao.readAllEvent()
    }

    fun readFavorite(): List<Event> {
        return eventDao.readFavorite()
    }

    fun readEventByCodeName(codeName: String): List<Event> {
        return eventDao.readEventByCodeName(codeName)
    }

    fun readAllEventOldest(): List<Event> {
        return eventDao.readAllEventOldest()
    }

    fun readAllEventNewest(): List<Event> {
        return eventDao.readAllEventNewest()
    }

    fun readEventOldest(codeName: String): List<Event> {
        return eventDao.readEventOldest(codeName)
    }

    fun readEventNewest(codeName: String): List<Event> {
        return eventDao.readEventNewest(codeName)
    }

    fun readAllEventFree(fee: String): List<Event> {
        return eventDao.readAllEventFree(fee)
    }

    fun readEventFree(codeName: String, fee: String): List<Event> {
        return eventDao.readEventFree(codeName, fee)
    }

}