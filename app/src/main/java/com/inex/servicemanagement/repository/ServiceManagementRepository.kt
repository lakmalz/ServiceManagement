package com.inex.servicemanagement.repository

import com.inex.servicemanagement.data.model.ServiceType
import com.inex.servicemanagement.data.cloud.ServiceManagementServices

class ServiceManagementRepository(private var api: ServiceManagementServices) {

    suspend fun getServiceList() = api.getServiceList()

    suspend fun saveOrUpdateService(request: ServiceType) = api.saveOrUpdateService(request)

    suspend fun deleteService(id: Long) = api.deleteService(id)

}