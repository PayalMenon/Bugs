package com.joinhonor.bugtracker.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.joinhonor.bugtracker.ApiRepository
import com.joinhonor.bugtracker.Bug
import com.joinhonor.bugtracker.DeleteBugResponse
import com.joinhonor.bugtracker.GetBugsResponse
import com.joinhonor.bugtracker.util.Event
import com.joinhonor.bugtracker.util.NetworkResult
import kotlinx.coroutines.launch
import javax.inject.Inject

class BugsViewModel @Inject constructor(application : Application,
                                        val apiRepository: ApiRepository) : AndroidViewModel(application) {


    //LiveData
    private val _showLoading = MutableLiveData<Event<Unit>>()
    val showLoading: LiveData<Event<Unit>>
        get() = _showLoading

    private val _hideLoading = MutableLiveData<Event<Unit>>()
    val hideLoading: LiveData<Event<Unit>>
        get() = _hideLoading

    private val _showList = MutableLiveData<Event<Unit>>()
    val showList: LiveData<Event<Unit>>
        get() = _showList

    private val _openAddBugFragment = MutableLiveData<Event<Unit>>()
    val openAddBugFragment: LiveData<Event<Unit>>
        get() = _openAddBugFragment

    private val _closeAddBugFragment = MutableLiveData<Event<Unit>>()
    val closeAddBugFragment: LiveData<Event<Unit>>
        get() = _closeAddBugFragment

    var bugs: MutableList<Bug> = mutableListOf()

    fun getBugsList() {

        viewModelScope.launch {
            // TODO: showing loading
            when(val response : NetworkResult<GetBugsResponse> = apiRepository.getBugsList()) {
                is NetworkResult.Success -> {
                    // TODO: hide loading
                    bugs = response.body.bugs.toMutableList()
                    _showList.value = Event(Unit)
                    Log.d("BugTracker", "Fetch List Successs")
                }
                is NetworkResult.Failure -> {
                    Log.d("BugTracker", "Fetch List Failure")
                    // TODO: hide loading
                    // TODO: show error and retry handling
                }
            }

        }
    }

    fun addBug(title: String, text: String) {
        viewModelScope.launch {
            when(val response : NetworkResult<Bug> = apiRepository.addBug(title, text)) {
                is NetworkResult.Success -> {
                    bugs.add(response.body)
                    _closeAddBugFragment.value = Event(Unit)
                    Log.d("BugTracker", "Add Successs")
                    // Todo: add bug to the existing bug list and refresh
                }
                is NetworkResult.Failure -> {
                    _closeAddBugFragment.value = Event(Unit)
                    Log.d("BugTracker", "Add failure")
                    // TODO: show toast that adding failed
                }
            }
        }
    }

    fun deleteBug(id: String) {
        viewModelScope.launch {
            when(val response : NetworkResult<DeleteBugResponse> = apiRepository.deleteBug(id)) {
                is NetworkResult.Success -> {
                    bugs = bugs.filterNot { it.id == id }.toMutableList()
                    Log.d("BugTracker", "Delete Success")
                    // Todo: remove bug from the existing bug list and refresh
                }
                is NetworkResult.Failure -> {
                    Log.d("BugTracker", "Delete Failure")
                    // TODO: show toast that adding failed
                }
            }
        }
    }

    fun getBugList() : List<Bug> {
        return bugs
    }

    fun onAddButtonPressed() {
        _openAddBugFragment.value = Event(Unit)
    }
}