package com.inex.servicemanagement.base


import android.app.Application
import com.facebook.stetho.Stetho
import com.inex.servicemanagement.di.appModule
import com.inex.servicemanagement.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
        startKoin {
            androidContext(this@BaseApplication)
            modules(listOf(appModule, viewModelModule))
        }
    }

}