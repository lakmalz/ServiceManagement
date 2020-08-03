package com.inex.servicemanagement.di


import com.inex.servicemanagement.data.cloud.ServiceManagementAPI
import com.inex.servicemanagement.repository.ServiceManagementRepository
import com.inex.servicemanagement.views.addnewservice.AddNewServiceViewModel
import com.inex.servicemanagement.views.dashboard.DashboardViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

var appModule: Module = module {

    single { ServiceManagementAPI().getAPI() }

    factory { ServiceManagementRepository(get()) }
}

val viewModelModule = module {
    viewModel { DashboardViewModel(get()) }
    viewModel { AddNewServiceViewModel(get()) }
}
