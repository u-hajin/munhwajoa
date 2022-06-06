package com.doremifa.munhwajoa.database

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class EventViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: EventRepository

    init {
        val eventDao = EventDatabase.getDatabase(application).EventDao()
        repository = EventRepository(eventDao)
    }

    class Factory(val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return EventViewModel(application) as T
        }

    }

    fun insertEvent(event: Event) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertEvent(event)
        }
    }

    fun updateFavorite(event: Event) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateFavorite(event)
        }
    }

    fun readAllEvent(): List<Event> {
        return repository.readAllEvent()
    }

    fun readFavorite(): List<Event> {
        return repository.readFavorite()
    }

    fun readEventByCodeName(codeName: String): List<Event> {
        return repository.readEventByCodeName(codeName)
    }

    fun readAllEventOldest(): List<Event> {
        return repository.readAllEventOldest()
    }

    fun readAllEventNewest(): List<Event> {
        return repository.readAllEventNewest()
    }

    fun readEventOldest(codeName: String): List<Event> {
        return repository.readEventOldest(codeName)
    }

    fun readEventNewest(codeName: String): List<Event> {
        return repository.readEventNewest(codeName)
    }

    fun readAllEventFree(fee: String): List<Event> {
        return repository.readAllEventFree(fee)
    }

    fun readEventFree(codeName: String, fee: String): List<Event> {
        return repository.readEventFree(codeName, fee)
    }

}