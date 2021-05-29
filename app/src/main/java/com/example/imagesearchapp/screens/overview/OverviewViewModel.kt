package com.example.imagesearchapp.screens.overview

import androidx.lifecycle.*
import com.example.imagesearchapp.network.UnsplashApi.retrofitService
import com.example.imagesearchapp.network.UnsplashResponse
import kotlinx.coroutines.launch

enum class Status {LOADING, DONE, ERROR}

class OverviewViewModel : ViewModel() {

    private val searchTerm = MutableLiveData<String>()

    private val _status = MutableLiveData<Status>()
    val status: LiveData<Status>
            get() = _status

    private val _response = MutableLiveData<List<UnsplashResponse.UnsplashResults>>()
    val response: LiveData<List<UnsplashResponse.UnsplashResults>>
            get() = _response

    init {
        searchTerm.value = "cat"
        getPhotos(searchTerm.value.toString())
    }

    private fun getPhotos(searchTerm: String){
        viewModelScope.launch {
            _status.value = Status.LOADING
            try {
                _response.value = retrofitService.getData(searchTerm, 1,30).results
                _status.value = Status.DONE
            }catch (e: Exception){
                 _response.value = ArrayList()
                _status.value = Status.ERROR
            }
        }
    }

    fun changeTerm(query: String){
        searchTerm.value = query
        getPhotos(searchTerm.value.toString())
    }

    fun retry(){
        getPhotos(searchTerm.value.toString())
    }
}