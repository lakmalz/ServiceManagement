package com.inex.servicemanagement.views.addnewservice

import androidx.lifecycle.MutableLiveData
import com.inex.servicemanagement.data.model.ServiceType
import com.inex.servicemanagement.repository.ServiceManagementRepository
import com.inex.servicemanagement.base.BaseViewModel
import kotlinx.coroutines.launch

class AddNewServiceViewModel(private var serviceManagementRepository: ServiceManagementRepository) :
    BaseViewModel() {

    var serviceTypeResponse: MutableLiveData<ServiceType> = MutableLiveData()

    fun saveOrUpdateService(request: ServiceType) {
        uiScope.launch {
            showProgress()
            val response = serviceManagementRepository.saveOrUpdateService(request)
            if (response.isSuccessful) {
                serviceTypeResponse.postValue(response.body())
            } else {
                serviceTypeResponse.postValue(null)
            }
            hideProgress()
        }
    }

}