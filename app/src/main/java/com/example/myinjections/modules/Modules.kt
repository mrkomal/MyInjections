package com.example.myinjections.modules

import android.app.Application
import androidx.room.Room
import com.example.myinjections.repository.InjectionsRepository
import com.example.myinjections.repository.InjectionsRepositoryImpl
import com.example.myinjections.room.database.InjectionsDatabase
import com.example.myinjections.room.model.InjectionsDao
import com.example.myinjections.viewmodel.InjectionsViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val databaseModule = module {
    fun provideDatabase(application: Application): InjectionsDatabase {
        return Room.databaseBuilder(application, InjectionsDatabase::class.java, "injections_database")
            .fallbackToDestructiveMigration()
            .build()
    }

    fun provideInjectionsDao(database: InjectionsDatabase): InjectionsDao {
        return  database.injectionsDao()
    }

    single { provideDatabase(androidApplication()) }
    single { provideInjectionsDao(get()) }
}


val repositoryModule = module {
    fun provideInjectionsRepository(dao : InjectionsDao): InjectionsRepository {
        return InjectionsRepositoryImpl(dao)
    }

    single { provideInjectionsRepository(get()) }
}


val viewModelModule = module {
    // Specific viewModel pattern to tell Koin how to build InjectionsViewModel
    viewModel { InjectionsViewModel(repository = get()) }
}