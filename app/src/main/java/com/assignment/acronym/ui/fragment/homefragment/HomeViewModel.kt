package com.assignment.acronym.ui.fragment.homefragment

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.assignment.acronym.ui.fragment.BaseViewModel
import com.assignment.acronym.utils.SingleLiveEvent
import com.assignment.data.model.networkmodel.AcronymResponse
import com.assignment.network.remote.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: Repository,
    application: Application
) : BaseViewModel(application) {

    private val _response: SingleLiveEvent<List<String>?> =
        SingleLiveEvent()
    val response: LiveData<List<String>?> = _response

    fun fetchByShortForm(sf: String) = viewModelScope.launch {
        showLoading()
        repository.getByShortForm(sf).collect {
            hideLoading()
            _response.value = transformResponseToList(it.data)
        }
    }

    fun fetchByLongForm(lf: String) = viewModelScope.launch {
        showLoading()
        repository.getByLongForm(lf).collect {
            hideLoading()
            _response.value = transformResponseToList(it.data)
        }
    }

    fun transformResponseToList(response: List<AcronymResponse>?): List<String> {
        val meanings = mutableListOf<String>()
        response?.map {
            it.lfs?.map { shortFormMeaning ->
                shortFormMeaning.lf
            }
        }?.forEach {
            it?.takeIf {
                it.isNotEmpty()
            }?.forEach { meaningItem ->
                meaningItem?.let { meaning ->
                    meanings.add(meaning)
                }
            }
        }
        return meanings
    }
}