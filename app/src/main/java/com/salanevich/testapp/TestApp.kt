package com.salanevich.testapp

import android.app.Application
import com.salanevich.testapp.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber.*

import timber.log.Timber

class TestApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidLogger()
            androidContext(this@TestApp)
            modules(appModule)
        }
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
    }
}