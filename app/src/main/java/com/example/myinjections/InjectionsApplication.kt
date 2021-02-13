package com.example.myinjections

import android.app.Application
import com.example.myinjections.modules.databaseModule
import com.example.myinjections.modules.recyclerViewAdapterModule
import com.example.myinjections.modules.repositoryModule
import com.example.myinjections.modules.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class InjectionsApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@InjectionsApplication)
            modules(
                databaseModule,
                repositoryModule,
                viewModelModule,
                recyclerViewAdapterModule
            )
        }
    }

}