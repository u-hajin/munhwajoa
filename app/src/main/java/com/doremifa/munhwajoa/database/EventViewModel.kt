package com.doremifa.munhwajoa.database

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
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

    fun addFavorite(event: Event) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addFavorite(event)
        }
    }

    fun deleteFavorite(title: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteFavorite(title)
        }
    }

    fun readAllFavorite(): LiveData<List<Event>> {
        return repository.readAllFavorite().asLiveData()
    }

    fun readTitleAsc(): LiveData<List<Event>> {
        return repository.readTitleAsc().asLiveData()
    }

}