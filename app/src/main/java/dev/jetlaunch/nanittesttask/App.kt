package dev.jetlaunch.nanittesttask

import android.app.Application
import dev.jetlaunch.nanittesttask.di.MainModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.core.context.startKoin

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            androidFileProperties()
            modules(MainModule)
        }
    }
}