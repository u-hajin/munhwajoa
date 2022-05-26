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

    fun addFavorite(event: Event) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addFavorite(event)
        }
    }

    fun deleteEvent(title: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteEvent(title)
        }
    }

    fun readAllEvent(): List<Event> {
        return repository.readAllEvent()
    }

    fun readTitleAsc(): List<Event> {
        return repository.readTitleAsc()
    }

    fun readFavorite(): List<Event> {
        return repository.readFavorite()
    }

    fun readEventByCodeName(codeName: String): List<Event> {
        return repository.readEventByCodeName(codeName)
    }

    fun readIncludeTitle(title: String): List<Event> {
        return repository.readIncludeTitle(title)
    }

}