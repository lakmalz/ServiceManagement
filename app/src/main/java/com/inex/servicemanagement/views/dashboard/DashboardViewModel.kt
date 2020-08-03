package com.inex.servicemanagement.views.dashboard


import androidx.lifecycle.MutableLiveData
import com.inex.servicemanagement.base.BaseViewModel
import com.inex.servicemanagement.data.model.ServiceType
import com.inex.servicemanagement.repository.ServiceManagementRepository
import kotlinx.coroutines.launch

class DashboardViewModel(private var serviceManagementRepository: ServiceManagementRepository) : BaseViewModel() {

    var dataServicesResponse: MutableLiveData<ArrayList<ServiceType>> = MutableLiveData()
    var deleteResponse: MutableLiveData<ServiceType?> = MutableLiveData()

    fun getDataServiceList() {
        uiScope.launch {
            showProgress()
            val response = serviceManagementRepository.getServiceList()
            if (response.isSuccessful) {
                val list = response.body() as ArrayList<ServiceType>
                dataServicesResponse.postValue(list)
            } else {
                dataServicesResponse.postValue(null)
            }
            hideProgress()
        }
    }

    fun deleteService(id: Long) {
        uiScope.launch {
            showProgress()
            val response = serviceManagementRepository.deleteService(id)
            if (response.isSuccessful) {
                deleteResponse.postValue(response.body() as ServiceType)
            } else {
                deleteResponse.postValue(null)
            }
            hideProgress()
        }
    }
}