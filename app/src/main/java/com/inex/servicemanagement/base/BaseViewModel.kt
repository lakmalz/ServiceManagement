package com.inex.servicemanagement.base

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

open class BaseViewModel : ViewModel() {
    var progressStatus: MutableLiveData<Int> = MutableLiveData()
    private val viewModelJob = SupervisorJob()
    protected val uiScope = CoroutineScope(Dispatchers.IO + viewModelJob)

    fun showProgress() {
        progressStatus.postValue(View.VISIBLE)
    }
    fun hideProgress() {
        progressStatus.postValue(View.GONE)
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}