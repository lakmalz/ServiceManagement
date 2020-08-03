package com.inex.servicemanagement.data.cloud

import com.inex.servicemanagement.data.model.ServiceType
import retrofit2.Response
import retrofit2.http.*

interface ServiceManagementServices {

    @GET("api/servicetype")
    suspend fun getServiceList(): Response<ArrayList<ServiceType>>

    @POST("api/servicetype")
    suspend fun saveOrUpdateService(@Body request: ServiceType): Response<ServiceType>

    @DELETE("api/servicetype/{id}")
    suspend fun deleteService(@Path("id") id: Long): Response<ServiceType>
}